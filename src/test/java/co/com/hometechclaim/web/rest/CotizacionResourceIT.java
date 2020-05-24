package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Cotizacion;
import co.com.hometechclaim.domain.Cliente;
import co.com.hometechclaim.repository.CotizacionRepository;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static co.com.hometechclaim.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.hometechclaim.domain.enumeration.Estadocotizacion;
/**
 * Integration tests for the {@link CotizacionResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class CotizacionResourceIT {

    private static final Instant DEFAULT_FECHACREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHACREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALORIVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALORIVA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTALSINIVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTALSINIVA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PORCENTAJEDESCUENTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PORCENTAJEDESCUENTO = new BigDecimal(2);

    private static final Instant DEFAULT_FECHAMANTENIMIENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHAMANTENIMIENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    private static final Estadocotizacion DEFAULT_ESTADOCOTIZACION = Estadocotizacion.INICIO;
    private static final Estadocotizacion UPDATED_ESTADOCOTIZACION = Estadocotizacion.ENPROCESO;

    @Autowired
    private CotizacionRepository cotizacionRepository;

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

    private MockMvc restCotizacionMockMvc;

    private Cotizacion cotizacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CotizacionResource cotizacionResource = new CotizacionResource(cotizacionRepository);
        this.restCotizacionMockMvc = MockMvcBuilders.standaloneSetup(cotizacionResource)
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
    public static Cotizacion createEntity(EntityManager em) {
        Cotizacion cotizacion = new Cotizacion()
            .fechacreacion(DEFAULT_FECHACREACION)
            .descripcion(DEFAULT_DESCRIPCION)
            .valoriva(DEFAULT_VALORIVA)
            .totalsiniva(DEFAULT_TOTALSINIVA)
            .porcentajedescuento(DEFAULT_PORCENTAJEDESCUENTO)
            .fechamantenimiento(DEFAULT_FECHAMANTENIMIENTO)
            .activo(DEFAULT_ACTIVO)
            .total(DEFAULT_TOTAL)
            .estadocotizacion(DEFAULT_ESTADOCOTIZACION);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        cotizacion.setCliente(cliente);
        return cotizacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cotizacion createUpdatedEntity(EntityManager em) {
        Cotizacion cotizacion = new Cotizacion()
            .fechacreacion(UPDATED_FECHACREACION)
            .descripcion(UPDATED_DESCRIPCION)
            .valoriva(UPDATED_VALORIVA)
            .totalsiniva(UPDATED_TOTALSINIVA)
            .porcentajedescuento(UPDATED_PORCENTAJEDESCUENTO)
            .fechamantenimiento(UPDATED_FECHAMANTENIMIENTO)
            .activo(UPDATED_ACTIVO)
            .total(UPDATED_TOTAL)
            .estadocotizacion(UPDATED_ESTADOCOTIZACION);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createUpdatedEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        cotizacion.setCliente(cliente);
        return cotizacion;
    }

    @BeforeEach
    public void initTest() {
        cotizacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCotizacion() throws Exception {
        int databaseSizeBeforeCreate = cotizacionRepository.findAll().size();

        // Create the Cotizacion
        restCotizacionMockMvc.perform(post("/api/cotizacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
            .andExpect(status().isCreated());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeCreate + 1);
        Cotizacion testCotizacion = cotizacionList.get(cotizacionList.size() - 1);
        assertThat(testCotizacion.getFechacreacion()).isEqualTo(DEFAULT_FECHACREACION);
        assertThat(testCotizacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCotizacion.getValoriva()).isEqualTo(DEFAULT_VALORIVA);
        assertThat(testCotizacion.getTotalsiniva()).isEqualTo(DEFAULT_TOTALSINIVA);
        assertThat(testCotizacion.getPorcentajedescuento()).isEqualTo(DEFAULT_PORCENTAJEDESCUENTO);
        assertThat(testCotizacion.getFechamantenimiento()).isEqualTo(DEFAULT_FECHAMANTENIMIENTO);
        assertThat(testCotizacion.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testCotizacion.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testCotizacion.getEstadocotizacion()).isEqualTo(DEFAULT_ESTADOCOTIZACION);
    }

    @Test
    @Transactional
    public void createCotizacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cotizacionRepository.findAll().size();

        // Create the Cotizacion with an existing ID
        cotizacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCotizacionMockMvc.perform(post("/api/cotizacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
            .andExpect(status().isBadRequest());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setDescripcion(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
            .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorivaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setValoriva(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
            .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalsinivaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setTotalsiniva(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
            .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPorcentajedescuentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setPorcentajedescuento(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
            .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setTotal(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
            .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCotizacions() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        // Get all the cotizacionList
        restCotizacionMockMvc.perform(get("/api/cotizacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cotizacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechacreacion").value(hasItem(DEFAULT_FECHACREACION.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].valoriva").value(hasItem(DEFAULT_VALORIVA.intValue())))
            .andExpect(jsonPath("$.[*].totalsiniva").value(hasItem(DEFAULT_TOTALSINIVA.intValue())))
            .andExpect(jsonPath("$.[*].porcentajedescuento").value(hasItem(DEFAULT_PORCENTAJEDESCUENTO.intValue())))
            .andExpect(jsonPath("$.[*].fechamantenimiento").value(hasItem(DEFAULT_FECHAMANTENIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].estadocotizacion").value(hasItem(DEFAULT_ESTADOCOTIZACION.toString())));
    }
    
    @Test
    @Transactional
    public void getCotizacion() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        // Get the cotizacion
        restCotizacionMockMvc.perform(get("/api/cotizacions/{id}", cotizacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cotizacion.getId().intValue()))
            .andExpect(jsonPath("$.fechacreacion").value(DEFAULT_FECHACREACION.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.valoriva").value(DEFAULT_VALORIVA.intValue()))
            .andExpect(jsonPath("$.totalsiniva").value(DEFAULT_TOTALSINIVA.intValue()))
            .andExpect(jsonPath("$.porcentajedescuento").value(DEFAULT_PORCENTAJEDESCUENTO.intValue()))
            .andExpect(jsonPath("$.fechamantenimiento").value(DEFAULT_FECHAMANTENIMIENTO.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()))
            .andExpect(jsonPath("$.estadocotizacion").value(DEFAULT_ESTADOCOTIZACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCotizacion() throws Exception {
        // Get the cotizacion
        restCotizacionMockMvc.perform(get("/api/cotizacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCotizacion() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();

        // Update the cotizacion
        Cotizacion updatedCotizacion = cotizacionRepository.findById(cotizacion.getId()).get();
        // Disconnect from session so that the updates on updatedCotizacion are not directly saved in db
        em.detach(updatedCotizacion);
        updatedCotizacion
            .fechacreacion(UPDATED_FECHACREACION)
            .descripcion(UPDATED_DESCRIPCION)
            .valoriva(UPDATED_VALORIVA)
            .totalsiniva(UPDATED_TOTALSINIVA)
            .porcentajedescuento(UPDATED_PORCENTAJEDESCUENTO)
            .fechamantenimiento(UPDATED_FECHAMANTENIMIENTO)
            .activo(UPDATED_ACTIVO)
            .total(UPDATED_TOTAL)
            .estadocotizacion(UPDATED_ESTADOCOTIZACION);

        restCotizacionMockMvc.perform(put("/api/cotizacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCotizacion)))
            .andExpect(status().isOk());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeUpdate);
        Cotizacion testCotizacion = cotizacionList.get(cotizacionList.size() - 1);
        assertThat(testCotizacion.getFechacreacion()).isEqualTo(UPDATED_FECHACREACION);
        assertThat(testCotizacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCotizacion.getValoriva()).isEqualTo(UPDATED_VALORIVA);
        assertThat(testCotizacion.getTotalsiniva()).isEqualTo(UPDATED_TOTALSINIVA);
        assertThat(testCotizacion.getPorcentajedescuento()).isEqualTo(UPDATED_PORCENTAJEDESCUENTO);
        assertThat(testCotizacion.getFechamantenimiento()).isEqualTo(UPDATED_FECHAMANTENIMIENTO);
        assertThat(testCotizacion.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testCotizacion.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testCotizacion.getEstadocotizacion()).isEqualTo(UPDATED_ESTADOCOTIZACION);
    }

    @Test
    @Transactional
    public void updateNonExistingCotizacion() throws Exception {
        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();

        // Create the Cotizacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCotizacionMockMvc.perform(put("/api/cotizacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
            .andExpect(status().isBadRequest());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCotizacion() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        int databaseSizeBeforeDelete = cotizacionRepository.findAll().size();

        // Delete the cotizacion
        restCotizacionMockMvc.perform(delete("/api/cotizacions/{id}", cotizacion.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
