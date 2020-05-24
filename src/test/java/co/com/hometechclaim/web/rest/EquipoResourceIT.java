package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Equipo;
import co.com.hometechclaim.domain.Marca;
import co.com.hometechclaim.repository.EquipoRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static co.com.hometechclaim.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EquipoResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class EquipoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROLADOR = "AAAAAAAAAA";
    private static final String UPDATED_CONTROLADOR = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNTNAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTROLLERMACADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CONTROLLERMACADDRESS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_URL_CONTENT_TYPE = "image/png";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    @Autowired
    private EquipoRepository equipoRepository;

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

    private MockMvc restEquipoMockMvc;

    private Equipo equipo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquipoResource equipoResource = new EquipoResource(equipoRepository);
        this.restEquipoMockMvc = MockMvcBuilders.standaloneSetup(equipoResource)
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
    public static Equipo createEntity(EntityManager em) {
        Equipo equipo = new Equipo()
            .nombre(DEFAULT_NOMBRE)
            .version(DEFAULT_VERSION)
            .controlador(DEFAULT_CONTROLADOR)
            .accountname(DEFAULT_ACCOUNTNAME)
            .controllermacaddress(DEFAULT_CONTROLLERMACADDRESS)
            .imagenUrl(DEFAULT_IMAGEN_URL)
            .imagenUrlContentType(DEFAULT_IMAGEN_URL_CONTENT_TYPE)
            .valor(DEFAULT_VALOR);
        // Add required entity
        Marca marca;
        if (TestUtil.findAll(em, Marca.class).isEmpty()) {
            marca = MarcaResourceIT.createEntity(em);
            em.persist(marca);
            em.flush();
        } else {
            marca = TestUtil.findAll(em, Marca.class).get(0);
        }
        equipo.setMarca(marca);
        return equipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipo createUpdatedEntity(EntityManager em) {
        Equipo equipo = new Equipo()
            .nombre(UPDATED_NOMBRE)
            .version(UPDATED_VERSION)
            .controlador(UPDATED_CONTROLADOR)
            .accountname(UPDATED_ACCOUNTNAME)
            .controllermacaddress(UPDATED_CONTROLLERMACADDRESS)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .valor(UPDATED_VALOR);
        // Add required entity
        Marca marca;
        if (TestUtil.findAll(em, Marca.class).isEmpty()) {
            marca = MarcaResourceIT.createUpdatedEntity(em);
            em.persist(marca);
            em.flush();
        } else {
            marca = TestUtil.findAll(em, Marca.class).get(0);
        }
        equipo.setMarca(marca);
        return equipo;
    }

    @BeforeEach
    public void initTest() {
        equipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipo() throws Exception {
        int databaseSizeBeforeCreate = equipoRepository.findAll().size();

        // Create the Equipo
        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isCreated());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeCreate + 1);
        Equipo testEquipo = equipoList.get(equipoList.size() - 1);
        assertThat(testEquipo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEquipo.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testEquipo.getControlador()).isEqualTo(DEFAULT_CONTROLADOR);
        assertThat(testEquipo.getAccountname()).isEqualTo(DEFAULT_ACCOUNTNAME);
        assertThat(testEquipo.getControllermacaddress()).isEqualTo(DEFAULT_CONTROLLERMACADDRESS);
        assertThat(testEquipo.getImagenUrl()).isEqualTo(DEFAULT_IMAGEN_URL);
        assertThat(testEquipo.getImagenUrlContentType()).isEqualTo(DEFAULT_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testEquipo.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createEquipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipoRepository.findAll().size();

        // Create the Equipo with an existing ID
        equipo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isBadRequest());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setNombre(null);

        // Create the Equipo, which fails.

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setVersion(null);

        // Create the Equipo, which fails.

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkControladorIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setControlador(null);

        // Create the Equipo, which fails.

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setValor(null);

        // Create the Equipo, which fails.

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquipos() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        // Get all the equipoList
        restEquipoMockMvc.perform(get("/api/equipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].controlador").value(hasItem(DEFAULT_CONTROLADOR)))
            .andExpect(jsonPath("$.[*].accountname").value(hasItem(DEFAULT_ACCOUNTNAME)))
            .andExpect(jsonPath("$.[*].controllermacaddress").value(hasItem(DEFAULT_CONTROLLERMACADDRESS)))
            .andExpect(jsonPath("$.[*].imagenUrlContentType").value(hasItem(DEFAULT_IMAGEN_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL))))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }
    
    @Test
    @Transactional
    public void getEquipo() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        // Get the equipo
        restEquipoMockMvc.perform(get("/api/equipos/{id}", equipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.controlador").value(DEFAULT_CONTROLADOR))
            .andExpect(jsonPath("$.accountname").value(DEFAULT_ACCOUNTNAME))
            .andExpect(jsonPath("$.controllermacaddress").value(DEFAULT_CONTROLLERMACADDRESS))
            .andExpect(jsonPath("$.imagenUrlContentType").value(DEFAULT_IMAGEN_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenUrl").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_URL)))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEquipo() throws Exception {
        // Get the equipo
        restEquipoMockMvc.perform(get("/api/equipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipo() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        int databaseSizeBeforeUpdate = equipoRepository.findAll().size();

        // Update the equipo
        Equipo updatedEquipo = equipoRepository.findById(equipo.getId()).get();
        // Disconnect from session so that the updates on updatedEquipo are not directly saved in db
        em.detach(updatedEquipo);
        updatedEquipo
            .nombre(UPDATED_NOMBRE)
            .version(UPDATED_VERSION)
            .controlador(UPDATED_CONTROLADOR)
            .accountname(UPDATED_ACCOUNTNAME)
            .controllermacaddress(UPDATED_CONTROLLERMACADDRESS)
            .imagenUrl(UPDATED_IMAGEN_URL)
            .imagenUrlContentType(UPDATED_IMAGEN_URL_CONTENT_TYPE)
            .valor(UPDATED_VALOR);

        restEquipoMockMvc.perform(put("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipo)))
            .andExpect(status().isOk());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeUpdate);
        Equipo testEquipo = equipoList.get(equipoList.size() - 1);
        assertThat(testEquipo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEquipo.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testEquipo.getControlador()).isEqualTo(UPDATED_CONTROLADOR);
        assertThat(testEquipo.getAccountname()).isEqualTo(UPDATED_ACCOUNTNAME);
        assertThat(testEquipo.getControllermacaddress()).isEqualTo(UPDATED_CONTROLLERMACADDRESS);
        assertThat(testEquipo.getImagenUrl()).isEqualTo(UPDATED_IMAGEN_URL);
        assertThat(testEquipo.getImagenUrlContentType()).isEqualTo(UPDATED_IMAGEN_URL_CONTENT_TYPE);
        assertThat(testEquipo.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipo() throws Exception {
        int databaseSizeBeforeUpdate = equipoRepository.findAll().size();

        // Create the Equipo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipoMockMvc.perform(put("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isBadRequest());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquipo() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        int databaseSizeBeforeDelete = equipoRepository.findAll().size();

        // Delete the equipo
        restEquipoMockMvc.perform(delete("/api/equipos/{id}", equipo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
