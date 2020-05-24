package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.TipoSolucion;
import co.com.hometechclaim.repository.TipoSolucionRepository;
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
 * Integration tests for the {@link TipoSolucionResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class TipoSolucionResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_URL_CONTENT_TYPE = "image/png";

    @Autowired
    private TipoSolucionRepository tipoSolucionRepository;

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

    private MockMvc restTipoSolucionMockMvc;

    private TipoSolucion tipoSolucion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoSolucionResource tipoSolucionResource = new TipoSolucionResource(tipoSolucionRepository);
        this.restTipoSolucionMockMvc = MockMvcBuilders.standaloneSetup(tipoSolucionResource)
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
    public static TipoSolucion createEntity(EntityManager em) {
        TipoSolucion tipoSolucion = new TipoSolucion()
            .nombre(DEFAULT_NOMBRE)
            .code(DEFAULT_CODE)
            .imagenUrl(DEFAULT_IMAGEN_URL)
            .imagenUrlContentType(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
        return tipoSolucion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoSolucion createUpdatedEntity(EntityManager em) {
        TipoSolucion tipoSolucion = new TipoSolucion()
            .nombre(UPDATED_NOMBRE)
            .code(UPDATED_CODE)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE);
        return tipoSolucion;
    }

    @BeforeEach
    public void initTest() {
        tipoSolucion = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoSolucion() throws Exception {
        int databaseSizeBeforeCreate = tipoSolucionRepository.findAll().size();

        // Create the TipoSolucion
        restTipoSolucionMockMvc.perform(post("/api/tipo-solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoSolucion)))
            .andExpect(status().isCreated());

        // Validate the TipoSolucion in the database
        List<TipoSolucion> tipoSolucionList = tipoSolucionRepository.findAll();
        assertThat(tipoSolucionList).hasSize(databaseSizeBeforeCreate + 1);
        TipoSolucion testTipoSolucion = tipoSolucionList.get(tipoSolucionList.size() - 1);
        assertThat(testTipoSolucion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoSolucion.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTipoSolucion.getImagenUrl()).isEqualTo(DEFAULT_IMAGEN_URL);
        assertThat(testTipoSolucion.getImagenUrlContentType()).isEqualTo(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createTipoSolucionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoSolucionRepository.findAll().size();

        // Create the TipoSolucion with an existing ID
        tipoSolucion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoSolucionMockMvc.perform(post("/api/tipo-solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoSolucion)))
            .andExpect(status().isBadRequest());

        // Validate the TipoSolucion in the database
        List<TipoSolucion> tipoSolucionList = tipoSolucionRepository.findAll();
        assertThat(tipoSolucionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoSolucionRepository.findAll().size();
        // set the field null
        tipoSolucion.setNombre(null);

        // Create the TipoSolucion, which fails.

        restTipoSolucionMockMvc.perform(post("/api/tipo-solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoSolucion)))
            .andExpect(status().isBadRequest());

        List<TipoSolucion> tipoSolucionList = tipoSolucionRepository.findAll();
        assertThat(tipoSolucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoSolucionRepository.findAll().size();
        // set the field null
        tipoSolucion.setCode(null);

        // Create the TipoSolucion, which fails.

        restTipoSolucionMockMvc.perform(post("/api/tipo-solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoSolucion)))
            .andExpect(status().isBadRequest());

        List<TipoSolucion> tipoSolucionList = tipoSolucionRepository.findAll();
        assertThat(tipoSolucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoSolucions() throws Exception {
        // Initialize the database
        tipoSolucionRepository.saveAndFlush(tipoSolucion);

        // Get all the tipoSolucionList
        restTipoSolucionMockMvc.perform(get("/api/tipo-solucions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoSolucion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].imagenUrlContentType").value(hasItem(DEFAULT_IMAGEN_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL))));
    }
    
    @Test
    @Transactional
    public void getTipoSolucion() throws Exception {
        // Initialize the database
        tipoSolucionRepository.saveAndFlush(tipoSolucion);

        // Get the tipoSolucion
        restTipoSolucionMockMvc.perform(get("/api/tipo-solucions/{id}", tipoSolucion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoSolucion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.imagenUrlContentType").value(DEFAULT_IMAGEN_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenUrl").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL)));
    }

    @Test
    @Transactional
    public void getNonExistingTipoSolucion() throws Exception {
        // Get the tipoSolucion
        restTipoSolucionMockMvc.perform(get("/api/tipo-solucions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoSolucion() throws Exception {
        // Initialize the database
        tipoSolucionRepository.saveAndFlush(tipoSolucion);

        int databaseSizeBeforeUpdate = tipoSolucionRepository.findAll().size();

        // Update the tipoSolucion
        TipoSolucion updatedTipoSolucion = tipoSolucionRepository.findById(tipoSolucion.getId()).get();
        // Disconnect from session so that the updates on updatedTipoSolucion are not directly saved in db
        em.detach(updatedTipoSolucion);
        updatedTipoSolucion
            .nombre(UPDATED_NOMBRE)
            .code(UPDATED_CODE)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE);

        restTipoSolucionMockMvc.perform(put("/api/tipo-solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoSolucion)))
            .andExpect(status().isOk());

        // Validate the TipoSolucion in the database
        List<TipoSolucion> tipoSolucionList = tipoSolucionRepository.findAll();
        assertThat(tipoSolucionList).hasSize(databaseSizeBeforeUpdate);
        TipoSolucion testTipoSolucion = tipoSolucionList.get(tipoSolucionList.size() - 1);
        assertThat(testTipoSolucion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoSolucion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTipoSolucion.getImagenUrl()).isEqualTo(UPDATED_IMAGEN_URL);
        assertThat(testTipoSolucion.getImagenUrlContentType()).isEqualTo(UPDATED_IMAGEN_URL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoSolucion() throws Exception {
        int databaseSizeBeforeUpdate = tipoSolucionRepository.findAll().size();

        // Create the TipoSolucion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoSolucionMockMvc.perform(put("/api/tipo-solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoSolucion)))
            .andExpect(status().isBadRequest());

        // Validate the TipoSolucion in the database
        List<TipoSolucion> tipoSolucionList = tipoSolucionRepository.findAll();
        assertThat(tipoSolucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoSolucion() throws Exception {
        // Initialize the database
        tipoSolucionRepository.saveAndFlush(tipoSolucion);

        int databaseSizeBeforeDelete = tipoSolucionRepository.findAll().size();

        // Delete the tipoSolucion
        restTipoSolucionMockMvc.perform(delete("/api/tipo-solucions/{id}", tipoSolucion.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoSolucion> tipoSolucionList = tipoSolucionRepository.findAll();
        assertThat(tipoSolucionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
