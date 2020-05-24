package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.EquiposcotizadosToEquipos;
import co.com.hometechclaim.domain.Cotizacion;
import co.com.hometechclaim.domain.Equipo;
import co.com.hometechclaim.repository.EquiposcotizadosToEquiposRepository;
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

/**
 * Integration tests for the {@link EquiposcotizadosToEquiposResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class EquiposcotizadosToEquiposResourceIT {

    private static final BigDecimal DEFAULT_VALOR_UNIDAD = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_UNIDAD = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_UTIL_UNIDAD = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_UTIL_UNIDAD = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DESCUENTO_UNIDAD = new BigDecimal(1);
    private static final BigDecimal UPDATED_DESCUENTO_UNIDAD = new BigDecimal(2);

    private static final Instant DEFAULT_FECHACOTIZACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHACOTIZACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_CANTIDAD_EQUIPOS = new BigDecimal(1);
    private static final BigDecimal UPDATED_CANTIDAD_EQUIPOS = new BigDecimal(2);

    @Autowired
    private EquiposcotizadosToEquiposRepository equiposcotizadosToEquiposRepository;

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

    private MockMvc restEquiposcotizadosToEquiposMockMvc;

    private EquiposcotizadosToEquipos equiposcotizadosToEquipos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquiposcotizadosToEquiposResource equiposcotizadosToEquiposResource = new EquiposcotizadosToEquiposResource(equiposcotizadosToEquiposRepository);
        this.restEquiposcotizadosToEquiposMockMvc = MockMvcBuilders.standaloneSetup(equiposcotizadosToEquiposResource)
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
    public static EquiposcotizadosToEquipos createEntity(EntityManager em) {
        EquiposcotizadosToEquipos equiposcotizadosToEquipos = new EquiposcotizadosToEquipos()
            .valorUnidad(DEFAULT_VALOR_UNIDAD)
            .valorUtilUnidad(DEFAULT_VALOR_UTIL_UNIDAD)
            .descuentoUnidad(DEFAULT_DESCUENTO_UNIDAD)
            .fechacotizacion(DEFAULT_FECHACOTIZACION)
            .cantidadEquipos(DEFAULT_CANTIDAD_EQUIPOS);
        // Add required entity
        Cotizacion cotizacion;
        if (TestUtil.findAll(em, Cotizacion.class).isEmpty()) {
            cotizacion = CotizacionResourceIT.createEntity(em);
            em.persist(cotizacion);
            em.flush();
        } else {
            cotizacion = TestUtil.findAll(em, Cotizacion.class).get(0);
        }
        equiposcotizadosToEquipos.setCotizacion(cotizacion);
        // Add required entity
        Equipo equipo;
        if (TestUtil.findAll(em, Equipo.class).isEmpty()) {
            equipo = EquipoResourceIT.createEntity(em);
            em.persist(equipo);
            em.flush();
        } else {
            equipo = TestUtil.findAll(em, Equipo.class).get(0);
        }
        equiposcotizadosToEquipos.setEquiposcotizadosToEquipos(equipo);
        return equiposcotizadosToEquipos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquiposcotizadosToEquipos createUpdatedEntity(EntityManager em) {
        EquiposcotizadosToEquipos equiposcotizadosToEquipos = new EquiposcotizadosToEquipos()
            .valorUnidad(UPDATED_VALOR_UNIDAD)
            .valorUtilUnidad(UPDATED_VALOR_UTIL_UNIDAD)
            .descuentoUnidad(UPDATED_DESCUENTO_UNIDAD)
            .fechacotizacion(UPDATED_FECHACOTIZACION)
            .cantidadEquipos(UPDATED_CANTIDAD_EQUIPOS);
        // Add required entity
        Cotizacion cotizacion;
        if (TestUtil.findAll(em, Cotizacion.class).isEmpty()) {
            cotizacion = CotizacionResourceIT.createUpdatedEntity(em);
            em.persist(cotizacion);
            em.flush();
        } else {
            cotizacion = TestUtil.findAll(em, Cotizacion.class).get(0);
        }
        equiposcotizadosToEquipos.setCotizacion(cotizacion);
        // Add required entity
        Equipo equipo;
        if (TestUtil.findAll(em, Equipo.class).isEmpty()) {
            equipo = EquipoResourceIT.createUpdatedEntity(em);
            em.persist(equipo);
            em.flush();
        } else {
            equipo = TestUtil.findAll(em, Equipo.class).get(0);
        }
        equiposcotizadosToEquipos.setEquiposcotizadosToEquipos(equipo);
        return equiposcotizadosToEquipos;
    }

    @BeforeEach
    public void initTest() {
        equiposcotizadosToEquipos = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquiposcotizadosToEquipos() throws Exception {
        int databaseSizeBeforeCreate = equiposcotizadosToEquiposRepository.findAll().size();

        // Create the EquiposcotizadosToEquipos
        restEquiposcotizadosToEquiposMockMvc.perform(post("/api/equiposcotizados-to-equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposcotizadosToEquipos)))
            .andExpect(status().isCreated());

        // Validate the EquiposcotizadosToEquipos in the database
        List<EquiposcotizadosToEquipos> equiposcotizadosToEquiposList = equiposcotizadosToEquiposRepository.findAll();
        assertThat(equiposcotizadosToEquiposList).hasSize(databaseSizeBeforeCreate + 1);
        EquiposcotizadosToEquipos testEquiposcotizadosToEquipos = equiposcotizadosToEquiposList.get(equiposcotizadosToEquiposList.size() - 1);
        assertThat(testEquiposcotizadosToEquipos.getValorUnidad()).isEqualTo(DEFAULT_VALOR_UNIDAD);
        assertThat(testEquiposcotizadosToEquipos.getValorUtilUnidad()).isEqualTo(DEFAULT_VALOR_UTIL_UNIDAD);
        assertThat(testEquiposcotizadosToEquipos.getDescuentoUnidad()).isEqualTo(DEFAULT_DESCUENTO_UNIDAD);
        assertThat(testEquiposcotizadosToEquipos.getFechacotizacion()).isEqualTo(DEFAULT_FECHACOTIZACION);
        assertThat(testEquiposcotizadosToEquipos.getCantidadEquipos()).isEqualTo(DEFAULT_CANTIDAD_EQUIPOS);
    }

    @Test
    @Transactional
    public void createEquiposcotizadosToEquiposWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equiposcotizadosToEquiposRepository.findAll().size();

        // Create the EquiposcotizadosToEquipos with an existing ID
        equiposcotizadosToEquipos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquiposcotizadosToEquiposMockMvc.perform(post("/api/equiposcotizados-to-equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposcotizadosToEquipos)))
            .andExpect(status().isBadRequest());

        // Validate the EquiposcotizadosToEquipos in the database
        List<EquiposcotizadosToEquipos> equiposcotizadosToEquiposList = equiposcotizadosToEquiposRepository.findAll();
        assertThat(equiposcotizadosToEquiposList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValorUnidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = equiposcotizadosToEquiposRepository.findAll().size();
        // set the field null
        equiposcotizadosToEquipos.setValorUnidad(null);

        // Create the EquiposcotizadosToEquipos, which fails.

        restEquiposcotizadosToEquiposMockMvc.perform(post("/api/equiposcotizados-to-equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposcotizadosToEquipos)))
            .andExpect(status().isBadRequest());

        List<EquiposcotizadosToEquipos> equiposcotizadosToEquiposList = equiposcotizadosToEquiposRepository.findAll();
        assertThat(equiposcotizadosToEquiposList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorUtilUnidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = equiposcotizadosToEquiposRepository.findAll().size();
        // set the field null
        equiposcotizadosToEquipos.setValorUtilUnidad(null);

        // Create the EquiposcotizadosToEquipos, which fails.

        restEquiposcotizadosToEquiposMockMvc.perform(post("/api/equiposcotizados-to-equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposcotizadosToEquipos)))
            .andExpect(status().isBadRequest());

        List<EquiposcotizadosToEquipos> equiposcotizadosToEquiposList = equiposcotizadosToEquiposRepository.findAll();
        assertThat(equiposcotizadosToEquiposList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescuentoUnidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = equiposcotizadosToEquiposRepository.findAll().size();
        // set the field null
        equiposcotizadosToEquipos.setDescuentoUnidad(null);

        // Create the EquiposcotizadosToEquipos, which fails.

        restEquiposcotizadosToEquiposMockMvc.perform(post("/api/equiposcotizados-to-equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposcotizadosToEquipos)))
            .andExpect(status().isBadRequest());

        List<EquiposcotizadosToEquipos> equiposcotizadosToEquiposList = equiposcotizadosToEquiposRepository.findAll();
        assertThat(equiposcotizadosToEquiposList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechacotizacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = equiposcotizadosToEquiposRepository.findAll().size();
        // set the field null
        equiposcotizadosToEquipos.setFechacotizacion(null);

        // Create the EquiposcotizadosToEquipos, which fails.

        restEquiposcotizadosToEquiposMockMvc.perform(post("/api/equiposcotizados-to-equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposcotizadosToEquipos)))
            .andExpect(status().isBadRequest());

        List<EquiposcotizadosToEquipos> equiposcotizadosToEquiposList = equiposcotizadosToEquiposRepository.findAll();
        assertThat(equiposcotizadosToEquiposList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadEquiposIsRequired() throws Exception {
        int databaseSizeBeforeTest = equiposcotizadosToEquiposRepository.findAll().size();
        // set the field null
        equiposcotizadosToEquipos.setCantidadEquipos(null);

        // Create the EquiposcotizadosToEquipos, which fails.

        restEquiposcotizadosToEquiposMockMvc.perform(post("/api/equiposcotizados-to-equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposcotizadosToEquipos)))
            .andExpect(status().isBadRequest());

        List<EquiposcotizadosToEquipos> equiposcotizadosToEquiposList = equiposcotizadosToEquiposRepository.findAll();
        assertThat(equiposcotizadosToEquiposList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquiposcotizadosToEquipos() throws Exception {
        // Initialize the database
        equiposcotizadosToEquiposRepository.saveAndFlush(equiposcotizadosToEquipos);

        // Get all the equiposcotizadosToEquiposList
        restEquiposcotizadosToEquiposMockMvc.perform(get("/api/equiposcotizados-to-equipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equiposcotizadosToEquipos.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorUnidad").value(hasItem(DEFAULT_VALOR_UNIDAD.intValue())))
            .andExpect(jsonPath("$.[*].valorUtilUnidad").value(hasItem(DEFAULT_VALOR_UTIL_UNIDAD.intValue())))
            .andExpect(jsonPath("$.[*].descuentoUnidad").value(hasItem(DEFAULT_DESCUENTO_UNIDAD.intValue())))
            .andExpect(jsonPath("$.[*].fechacotizacion").value(hasItem(DEFAULT_FECHACOTIZACION.toString())))
            .andExpect(jsonPath("$.[*].cantidadEquipos").value(hasItem(DEFAULT_CANTIDAD_EQUIPOS.intValue())));
    }
    
    @Test
    @Transactional
    public void getEquiposcotizadosToEquipos() throws Exception {
        // Initialize the database
        equiposcotizadosToEquiposRepository.saveAndFlush(equiposcotizadosToEquipos);

        // Get the equiposcotizadosToEquipos
        restEquiposcotizadosToEquiposMockMvc.perform(get("/api/equiposcotizados-to-equipos/{id}", equiposcotizadosToEquipos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equiposcotizadosToEquipos.getId().intValue()))
            .andExpect(jsonPath("$.valorUnidad").value(DEFAULT_VALOR_UNIDAD.intValue()))
            .andExpect(jsonPath("$.valorUtilUnidad").value(DEFAULT_VALOR_UTIL_UNIDAD.intValue()))
            .andExpect(jsonPath("$.descuentoUnidad").value(DEFAULT_DESCUENTO_UNIDAD.intValue()))
            .andExpect(jsonPath("$.fechacotizacion").value(DEFAULT_FECHACOTIZACION.toString()))
            .andExpect(jsonPath("$.cantidadEquipos").value(DEFAULT_CANTIDAD_EQUIPOS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEquiposcotizadosToEquipos() throws Exception {
        // Get the equiposcotizadosToEquipos
        restEquiposcotizadosToEquiposMockMvc.perform(get("/api/equiposcotizados-to-equipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquiposcotizadosToEquipos() throws Exception {
        // Initialize the database
        equiposcotizadosToEquiposRepository.saveAndFlush(equiposcotizadosToEquipos);

        int databaseSizeBeforeUpdate = equiposcotizadosToEquiposRepository.findAll().size();

        // Update the equiposcotizadosToEquipos
        EquiposcotizadosToEquipos updatedEquiposcotizadosToEquipos = equiposcotizadosToEquiposRepository.findById(equiposcotizadosToEquipos.getId()).get();
        // Disconnect from session so that the updates on updatedEquiposcotizadosToEquipos are not directly saved in db
        em.detach(updatedEquiposcotizadosToEquipos);
        updatedEquiposcotizadosToEquipos
            .valorUnidad(UPDATED_VALOR_UNIDAD)
            .valorUtilUnidad(UPDATED_VALOR_UTIL_UNIDAD)
            .descuentoUnidad(UPDATED_DESCUENTO_UNIDAD)
            .fechacotizacion(UPDATED_FECHACOTIZACION)
            .cantidadEquipos(UPDATED_CANTIDAD_EQUIPOS);

        restEquiposcotizadosToEquiposMockMvc.perform(put("/api/equiposcotizados-to-equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquiposcotizadosToEquipos)))
            .andExpect(status().isOk());

        // Validate the EquiposcotizadosToEquipos in the database
        List<EquiposcotizadosToEquipos> equiposcotizadosToEquiposList = equiposcotizadosToEquiposRepository.findAll();
        assertThat(equiposcotizadosToEquiposList).hasSize(databaseSizeBeforeUpdate);
        EquiposcotizadosToEquipos testEquiposcotizadosToEquipos = equiposcotizadosToEquiposList.get(equiposcotizadosToEquiposList.size() - 1);
        assertThat(testEquiposcotizadosToEquipos.getValorUnidad()).isEqualTo(UPDATED_VALOR_UNIDAD);
        assertThat(testEquiposcotizadosToEquipos.getValorUtilUnidad()).isEqualTo(UPDATED_VALOR_UTIL_UNIDAD);
        assertThat(testEquiposcotizadosToEquipos.getDescuentoUnidad()).isEqualTo(UPDATED_DESCUENTO_UNIDAD);
        assertThat(testEquiposcotizadosToEquipos.getFechacotizacion()).isEqualTo(UPDATED_FECHACOTIZACION);
        assertThat(testEquiposcotizadosToEquipos.getCantidadEquipos()).isEqualTo(UPDATED_CANTIDAD_EQUIPOS);
    }

    @Test
    @Transactional
    public void updateNonExistingEquiposcotizadosToEquipos() throws Exception {
        int databaseSizeBeforeUpdate = equiposcotizadosToEquiposRepository.findAll().size();

        // Create the EquiposcotizadosToEquipos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquiposcotizadosToEquiposMockMvc.perform(put("/api/equiposcotizados-to-equipos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposcotizadosToEquipos)))
            .andExpect(status().isBadRequest());

        // Validate the EquiposcotizadosToEquipos in the database
        List<EquiposcotizadosToEquipos> equiposcotizadosToEquiposList = equiposcotizadosToEquiposRepository.findAll();
        assertThat(equiposcotizadosToEquiposList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquiposcotizadosToEquipos() throws Exception {
        // Initialize the database
        equiposcotizadosToEquiposRepository.saveAndFlush(equiposcotizadosToEquipos);

        int databaseSizeBeforeDelete = equiposcotizadosToEquiposRepository.findAll().size();

        // Delete the equiposcotizadosToEquipos
        restEquiposcotizadosToEquiposMockMvc.perform(delete("/api/equiposcotizados-to-equipos/{id}", equiposcotizadosToEquipos.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EquiposcotizadosToEquipos> equiposcotizadosToEquiposList = equiposcotizadosToEquiposRepository.findAll();
        assertThat(equiposcotizadosToEquiposList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
