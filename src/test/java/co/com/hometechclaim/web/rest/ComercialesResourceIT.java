package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Comerciales;
import co.com.hometechclaim.repository.ComercialesRepository;
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
 * Integration tests for the {@link ComercialesResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class ComercialesResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_ZONA = "AAAAAAAAAA";
    private static final String UPDATED_ZONA = "BBBBBBBBBB";

    private static final Long DEFAULT_IDCIUDAD = 1L;
    private static final Long UPDATED_IDCIUDAD = 2L;

    private static final String DEFAULT_IDUSUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDUSUARIO = "BBBBBBBBBB";

    @Autowired
    private ComercialesRepository comercialesRepository;

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

    private MockMvc restComercialesMockMvc;

    private Comerciales comerciales;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComercialesResource comercialesResource = new ComercialesResource(comercialesRepository);
        this.restComercialesMockMvc = MockMvcBuilders.standaloneSetup(comercialesResource)
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
    public static Comerciales createEntity(EntityManager em) {
        Comerciales comerciales = new Comerciales()
            .codigo(DEFAULT_CODIGO)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .correo(DEFAULT_CORREO)
            .zona(DEFAULT_ZONA)
            .idciudad(DEFAULT_IDCIUDAD)
            .idusuario(DEFAULT_IDUSUARIO);
        return comerciales;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comerciales createUpdatedEntity(EntityManager em) {
        Comerciales comerciales = new Comerciales()
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .correo(UPDATED_CORREO)
            .zona(UPDATED_ZONA)
            .idciudad(UPDATED_IDCIUDAD)
            .idusuario(UPDATED_IDUSUARIO);
        return comerciales;
    }

    @BeforeEach
    public void initTest() {
        comerciales = createEntity(em);
    }

    @Test
    @Transactional
    public void createComerciales() throws Exception {
        int databaseSizeBeforeCreate = comercialesRepository.findAll().size();

        // Create the Comerciales
        restComercialesMockMvc.perform(post("/api/comerciales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comerciales)))
            .andExpect(status().isCreated());

        // Validate the Comerciales in the database
        List<Comerciales> comercialesList = comercialesRepository.findAll();
        assertThat(comercialesList).hasSize(databaseSizeBeforeCreate + 1);
        Comerciales testComerciales = comercialesList.get(comercialesList.size() - 1);
        assertThat(testComerciales.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testComerciales.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testComerciales.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testComerciales.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testComerciales.getZona()).isEqualTo(DEFAULT_ZONA);
        assertThat(testComerciales.getIdciudad()).isEqualTo(DEFAULT_IDCIUDAD);
        assertThat(testComerciales.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
    }

    @Test
    @Transactional
    public void createComercialesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comercialesRepository.findAll().size();

        // Create the Comerciales with an existing ID
        comerciales.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComercialesMockMvc.perform(post("/api/comerciales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comerciales)))
            .andExpect(status().isBadRequest());

        // Validate the Comerciales in the database
        List<Comerciales> comercialesList = comercialesRepository.findAll();
        assertThat(comercialesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = comercialesRepository.findAll().size();
        // set the field null
        comerciales.setCodigo(null);

        // Create the Comerciales, which fails.

        restComercialesMockMvc.perform(post("/api/comerciales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comerciales)))
            .andExpect(status().isBadRequest());

        List<Comerciales> comercialesList = comercialesRepository.findAll();
        assertThat(comercialesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = comercialesRepository.findAll().size();
        // set the field null
        comerciales.setNombre(null);

        // Create the Comerciales, which fails.

        restComercialesMockMvc.perform(post("/api/comerciales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comerciales)))
            .andExpect(status().isBadRequest());

        List<Comerciales> comercialesList = comercialesRepository.findAll();
        assertThat(comercialesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComerciales() throws Exception {
        // Initialize the database
        comercialesRepository.saveAndFlush(comerciales);

        // Get all the comercialesList
        restComercialesMockMvc.perform(get("/api/comerciales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comerciales.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].zona").value(hasItem(DEFAULT_ZONA)))
            .andExpect(jsonPath("$.[*].idciudad").value(hasItem(DEFAULT_IDCIUDAD.intValue())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO)));
    }
    
    @Test
    @Transactional
    public void getComerciales() throws Exception {
        // Initialize the database
        comercialesRepository.saveAndFlush(comerciales);

        // Get the comerciales
        restComercialesMockMvc.perform(get("/api/comerciales/{id}", comerciales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comerciales.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.zona").value(DEFAULT_ZONA))
            .andExpect(jsonPath("$.idciudad").value(DEFAULT_IDCIUDAD.intValue()))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO));
    }

    @Test
    @Transactional
    public void getNonExistingComerciales() throws Exception {
        // Get the comerciales
        restComercialesMockMvc.perform(get("/api/comerciales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComerciales() throws Exception {
        // Initialize the database
        comercialesRepository.saveAndFlush(comerciales);

        int databaseSizeBeforeUpdate = comercialesRepository.findAll().size();

        // Update the comerciales
        Comerciales updatedComerciales = comercialesRepository.findById(comerciales.getId()).get();
        // Disconnect from session so that the updates on updatedComerciales are not directly saved in db
        em.detach(updatedComerciales);
        updatedComerciales
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .correo(UPDATED_CORREO)
            .zona(UPDATED_ZONA)
            .idciudad(UPDATED_IDCIUDAD)
            .idusuario(UPDATED_IDUSUARIO);

        restComercialesMockMvc.perform(put("/api/comerciales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedComerciales)))
            .andExpect(status().isOk());

        // Validate the Comerciales in the database
        List<Comerciales> comercialesList = comercialesRepository.findAll();
        assertThat(comercialesList).hasSize(databaseSizeBeforeUpdate);
        Comerciales testComerciales = comercialesList.get(comercialesList.size() - 1);
        assertThat(testComerciales.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testComerciales.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testComerciales.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testComerciales.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testComerciales.getZona()).isEqualTo(UPDATED_ZONA);
        assertThat(testComerciales.getIdciudad()).isEqualTo(UPDATED_IDCIUDAD);
        assertThat(testComerciales.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingComerciales() throws Exception {
        int databaseSizeBeforeUpdate = comercialesRepository.findAll().size();

        // Create the Comerciales

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComercialesMockMvc.perform(put("/api/comerciales")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comerciales)))
            .andExpect(status().isBadRequest());

        // Validate the Comerciales in the database
        List<Comerciales> comercialesList = comercialesRepository.findAll();
        assertThat(comercialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComerciales() throws Exception {
        // Initialize the database
        comercialesRepository.saveAndFlush(comerciales);

        int databaseSizeBeforeDelete = comercialesRepository.findAll().size();

        // Delete the comerciales
        restComercialesMockMvc.perform(delete("/api/comerciales/{id}", comerciales.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comerciales> comercialesList = comercialesRepository.findAll();
        assertThat(comercialesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
