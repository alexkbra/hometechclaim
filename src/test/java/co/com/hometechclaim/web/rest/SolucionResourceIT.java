package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Solucion;
import co.com.hometechclaim.domain.TipoSolucion;
import co.com.hometechclaim.repository.SolucionRepository;
import co.com.hometechclaim.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static co.com.hometechclaim.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SolucionResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class SolucionResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_URL_CONTENT_TYPE = "image/png";

    @Autowired
    private SolucionRepository solucionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSolucionMockMvc;

    private Solucion solucion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolucionResource solucionResource = new SolucionResource(solucionRepository);
        this.restSolucionMockMvc = MockMvcBuilders.standaloneSetup(solucionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solucion createEntity(EntityManager em) {
        Solucion solucion = new Solucion()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .code(DEFAULT_CODE)
            .imagenUrl(DEFAULT_IMAGEN_URL)
            .imagenUrlContentType(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
        // Add required entity
        TipoSolucion tipoSolucion;
        if (TestUtil.findAll(em, TipoSolucion.class).isEmpty()) {
            tipoSolucion = TipoSolucionResourceIT.createEntity(em);
            em.persist(tipoSolucion);
            em.flush();
        } else {
            tipoSolucion = TestUtil.findAll(em, TipoSolucion.class).get(0);
        }
        solucion.setTipoSolucion(tipoSolucion);
        return solucion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solucion createUpdatedEntity(EntityManager em) {
        Solucion solucion = new Solucion()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .code(UPDATED_CODE)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE);
        // Add required entity
        TipoSolucion tipoSolucion;
        if (TestUtil.findAll(em, TipoSolucion.class).isEmpty()) {
            tipoSolucion = TipoSolucionResourceIT.createUpdatedEntity(em);
            em.persist(tipoSolucion);
            em.flush();
        } else {
            tipoSolucion = TestUtil.findAll(em, TipoSolucion.class).get(0);
        }
        solucion.setTipoSolucion(tipoSolucion);
        return solucion;
    }

    @BeforeEach
    public void initTest() {
        solucion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolucion() throws Exception {
        int databaseSizeBeforeCreate = solucionRepository.findAll().size();

        // Create the Solucion
        restSolucionMockMvc.perform(post("/api/solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solucion)))
            .andExpect(status().isCreated());

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll();
        assertThat(solucionList).hasSize(databaseSizeBeforeCreate + 1);
        Solucion testSolucion = solucionList.get(solucionList.size() - 1);
        assertThat(testSolucion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSolucion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSolucion.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSolucion.getImagenUrl()).isEqualTo(DEFAULT_IMAGEN_URL);
        assertThat(testSolucion.getImagenUrlContentType()).isEqualTo(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createSolucionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solucionRepository.findAll().size();

        // Create the Solucion with an existing ID
        solucion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolucionMockMvc.perform(post("/api/solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solucion)))
            .andExpect(status().isBadRequest());

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll();
        assertThat(solucionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = solucionRepository.findAll().size();
        // set the field null
        solucion.setNombre(null);

        // Create the Solucion, which fails.

        restSolucionMockMvc.perform(post("/api/solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solucion)))
            .andExpect(status().isBadRequest());

        List<Solucion> solucionList = solucionRepository.findAll();
        assertThat(solucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = solucionRepository.findAll().size();
        // set the field null
        solucion.setCode(null);

        // Create the Solucion, which fails.

        restSolucionMockMvc.perform(post("/api/solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solucion)))
            .andExpect(status().isBadRequest());

        List<Solucion> solucionList = solucionRepository.findAll();
        assertThat(solucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSolucions() throws Exception {
        // Initialize the database
        solucionRepository.saveAndFlush(solucion);

        // Get all the solucionList
        restSolucionMockMvc.perform(get("/api/solucions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solucion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].imagenUrlContentType").value(hasItem(DEFAULT_IMAGEN_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL))));
    }
    
    @Test
    @Transactional
    public void getSolucion() throws Exception {
        // Initialize the database
        solucionRepository.saveAndFlush(solucion);

        // Get the solucion
        restSolucionMockMvc.perform(get("/api/solucions/{id}", solucion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(solucion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.imagenUrlContentType").value(DEFAULT_IMAGEN_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenUrl").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL)));
    }

    @Test
    @Transactional
    public void getNonExistingSolucion() throws Exception {
        // Get the solucion
        restSolucionMockMvc.perform(get("/api/solucions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolucion() throws Exception {
        // Initialize the database
        solucionRepository.saveAndFlush(solucion);

        int databaseSizeBeforeUpdate = solucionRepository.findAll().size();

        // Update the solucion
        Solucion updatedSolucion = solucionRepository.findById(solucion.getId()).get();
        // Disconnect from session so that the updates on updatedSolucion are not directly saved in db
        em.detach(updatedSolucion);
        updatedSolucion
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .code(UPDATED_CODE)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE);

        restSolucionMockMvc.perform(put("/api/solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSolucion)))
            .andExpect(status().isOk());

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll();
        assertThat(solucionList).hasSize(databaseSizeBeforeUpdate);
        Solucion testSolucion = solucionList.get(solucionList.size() - 1);
        assertThat(testSolucion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSolucion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSolucion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSolucion.getImagenUrl()).isEqualTo(UPDATED_IMAGEN_URL);
        assertThat(testSolucion.getImagenUrlContentType()).isEqualTo(UPDATED_IMAGEN_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSolucion() throws Exception {
        int databaseSizeBeforeUpdate = solucionRepository.findAll().size();

        // Create the Solucion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolucionMockMvc.perform(put("/api/solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solucion)))
            .andExpect(status().isBadRequest());

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll();
        assertThat(solucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSolucion() throws Exception {
        // Initialize the database
        solucionRepository.saveAndFlush(solucion);

        int databaseSizeBeforeDelete = solucionRepository.findAll().size();

        // Delete the solucion
        restSolucionMockMvc.perform(delete("/api/solucions/{id}", solucion.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Solucion> solucionList = solucionRepository.findAll();
        assertThat(solucionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
