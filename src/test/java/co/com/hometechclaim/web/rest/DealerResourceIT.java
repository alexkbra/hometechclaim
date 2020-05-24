package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Dealer;
import co.com.hometechclaim.domain.Comerciales;
import co.com.hometechclaim.repository.DealerRepository;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static co.com.hometechclaim.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DealerResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class DealerResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final Long DEFAULT_IDCIUDAD = 1L;
    private static final Long UPDATED_IDCIUDAD = 2L;

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONOFIJO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOFIJO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONOCELULAR = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOCELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_IDUSUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDUSUARIO = "BBBBBBBBBB";

    @Autowired
    private DealerRepository dealerRepository;

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

    private MockMvc restDealerMockMvc;

    private Dealer dealer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DealerResource dealerResource = new DealerResource(dealerRepository);
        this.restDealerMockMvc = MockMvcBuilders.standaloneSetup(dealerResource)
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
    public static Dealer createEntity(EntityManager em) {
        Dealer dealer = new Dealer()
            .nombre(DEFAULT_NOMBRE)
            .correo(DEFAULT_CORREO)
            .codigo(DEFAULT_CODIGO)
            .idciudad(DEFAULT_IDCIUDAD)
            .direccion(DEFAULT_DIRECCION)
            .telefonofijo(DEFAULT_TELEFONOFIJO)
            .telefonocelular(DEFAULT_TELEFONOCELULAR)
            .idusuario(DEFAULT_IDUSUARIO);
        // Add required entity
        Comerciales comerciales;
        if (TestUtil.findAll(em, Comerciales.class).isEmpty()) {
            comerciales = ComercialesResourceIT.createEntity(em);
            em.persist(comerciales);
            em.flush();
        } else {
            comerciales = TestUtil.findAll(em, Comerciales.class).get(0);
        }
        dealer.setComerciales(comerciales);
        return dealer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dealer createUpdatedEntity(EntityManager em) {
        Dealer dealer = new Dealer()
            .nombre(UPDATED_NOMBRE)
            .correo(UPDATED_CORREO)
            .codigo(UPDATED_CODIGO)
            .idciudad(UPDATED_IDCIUDAD)
            .direccion(UPDATED_DIRECCION)
            .telefonofijo(UPDATED_TELEFONOFIJO)
            .telefonocelular(UPDATED_TELEFONOCELULAR)
            .idusuario(UPDATED_IDUSUARIO);
        // Add required entity
        Comerciales comerciales;
        if (TestUtil.findAll(em, Comerciales.class).isEmpty()) {
            comerciales = ComercialesResourceIT.createUpdatedEntity(em);
            em.persist(comerciales);
            em.flush();
        } else {
            comerciales = TestUtil.findAll(em, Comerciales.class).get(0);
        }
        dealer.setComerciales(comerciales);
        return dealer;
    }

    @BeforeEach
    public void initTest() {
        dealer = createEntity(em);
    }

    @Test
    @Transactional
    public void createDealer() throws Exception {
        int databaseSizeBeforeCreate = dealerRepository.findAll().size();

        // Create the Dealer
        restDealerMockMvc.perform(post("/api/dealers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealer)))
            .andExpect(status().isCreated());

        // Validate the Dealer in the database
        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeCreate + 1);
        Dealer testDealer = dealerList.get(dealerList.size() - 1);
        assertThat(testDealer.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDealer.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testDealer.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testDealer.getIdciudad()).isEqualTo(DEFAULT_IDCIUDAD);
        assertThat(testDealer.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testDealer.getTelefonofijo()).isEqualTo(DEFAULT_TELEFONOFIJO);
        assertThat(testDealer.getTelefonocelular()).isEqualTo(DEFAULT_TELEFONOCELULAR);
        assertThat(testDealer.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
    }

    @Test
    @Transactional
    public void createDealerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dealerRepository.findAll().size();

        // Create the Dealer with an existing ID
        dealer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealerMockMvc.perform(post("/api/dealers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealer)))
            .andExpect(status().isBadRequest());

        // Validate the Dealer in the database
        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealerRepository.findAll().size();
        // set the field null
        dealer.setNombre(null);

        // Create the Dealer, which fails.

        restDealerMockMvc.perform(post("/api/dealers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealer)))
            .andExpect(status().isBadRequest());

        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealerRepository.findAll().size();
        // set the field null
        dealer.setDireccion(null);

        // Create the Dealer, which fails.

        restDealerMockMvc.perform(post("/api/dealers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealer)))
            .andExpect(status().isBadRequest());

        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDealers() throws Exception {
        // Initialize the database
        dealerRepository.saveAndFlush(dealer);

        // Get all the dealerList
        restDealerMockMvc.perform(get("/api/dealers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealer.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].idciudad").value(hasItem(DEFAULT_IDCIUDAD.intValue())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].telefonofijo").value(hasItem(DEFAULT_TELEFONOFIJO)))
            .andExpect(jsonPath("$.[*].telefonocelular").value(hasItem(DEFAULT_TELEFONOCELULAR)))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO)));
    }
    
    @Test
    @Transactional
    public void getDealer() throws Exception {
        // Initialize the database
        dealerRepository.saveAndFlush(dealer);

        // Get the dealer
        restDealerMockMvc.perform(get("/api/dealers/{id}", dealer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealer.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.idciudad").value(DEFAULT_IDCIUDAD.intValue()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.telefonofijo").value(DEFAULT_TELEFONOFIJO))
            .andExpect(jsonPath("$.telefonocelular").value(DEFAULT_TELEFONOCELULAR))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO));
    }

    @Test
    @Transactional
    public void getNonExistingDealer() throws Exception {
        // Get the dealer
        restDealerMockMvc.perform(get("/api/dealers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDealer() throws Exception {
        // Initialize the database
        dealerRepository.saveAndFlush(dealer);

        int databaseSizeBeforeUpdate = dealerRepository.findAll().size();

        // Update the dealer
        Dealer updatedDealer = dealerRepository.findById(dealer.getId()).get();
        // Disconnect from session so that the updates on updatedDealer are not directly saved in db
        em.detach(updatedDealer);
        updatedDealer
            .nombre(UPDATED_NOMBRE)
            .correo(UPDATED_CORREO)
            .codigo(UPDATED_CODIGO)
            .idciudad(UPDATED_IDCIUDAD)
            .direccion(UPDATED_DIRECCION)
            .telefonofijo(UPDATED_TELEFONOFIJO)
            .telefonocelular(UPDATED_TELEFONOCELULAR)
            .idusuario(UPDATED_IDUSUARIO);

        restDealerMockMvc.perform(put("/api/dealers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDealer)))
            .andExpect(status().isOk());

        // Validate the Dealer in the database
        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeUpdate);
        Dealer testDealer = dealerList.get(dealerList.size() - 1);
        assertThat(testDealer.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDealer.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testDealer.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testDealer.getIdciudad()).isEqualTo(UPDATED_IDCIUDAD);
        assertThat(testDealer.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testDealer.getTelefonofijo()).isEqualTo(UPDATED_TELEFONOFIJO);
        assertThat(testDealer.getTelefonocelular()).isEqualTo(UPDATED_TELEFONOCELULAR);
        assertThat(testDealer.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingDealer() throws Exception {
        int databaseSizeBeforeUpdate = dealerRepository.findAll().size();

        // Create the Dealer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealerMockMvc.perform(put("/api/dealers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dealer)))
            .andExpect(status().isBadRequest());

        // Validate the Dealer in the database
        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDealer() throws Exception {
        // Initialize the database
        dealerRepository.saveAndFlush(dealer);

        int databaseSizeBeforeDelete = dealerRepository.findAll().size();

        // Delete the dealer
        restDealerMockMvc.perform(delete("/api/dealers/{id}", dealer.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dealer> dealerList = dealerRepository.findAll();
        assertThat(dealerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
