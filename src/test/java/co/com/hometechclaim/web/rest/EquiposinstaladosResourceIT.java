package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Equiposinstalados;
import co.com.hometechclaim.domain.Cliente;
import co.com.hometechclaim.domain.Equipo;
import co.com.hometechclaim.domain.Proyecto;
import co.com.hometechclaim.repository.EquiposinstaladosRepository;
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
 * Integration tests for the {@link EquiposinstaladosResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class EquiposinstaladosResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHAINSTALACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHAINSTALACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_POSIBLEACTULIAZCION = false;
    private static final Boolean UPDATED_POSIBLEACTULIAZCION = true;

    private static final BigDecimal DEFAULT_CANTIDAD = new BigDecimal(1);
    private static final BigDecimal UPDATED_CANTIDAD = new BigDecimal(2);

    @Autowired
    private EquiposinstaladosRepository equiposinstaladosRepository;

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

    private MockMvc restEquiposinstaladosMockMvc;

    private Equiposinstalados equiposinstalados;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquiposinstaladosResource equiposinstaladosResource = new EquiposinstaladosResource(equiposinstaladosRepository);
        this.restEquiposinstaladosMockMvc = MockMvcBuilders.standaloneSetup(equiposinstaladosResource)
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
    public static Equiposinstalados createEntity(EntityManager em) {
        Equiposinstalados equiposinstalados = new Equiposinstalados()
            .descripcion(DEFAULT_DESCRIPCION)
            .observacion(DEFAULT_OBSERVACION)
            .fechainstalacion(DEFAULT_FECHAINSTALACION)
            .posibleactuliazcion(DEFAULT_POSIBLEACTULIAZCION)
            .cantidad(DEFAULT_CANTIDAD);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        equiposinstalados.setCliente(cliente);
        // Add required entity
        Equipo equipo;
        if (TestUtil.findAll(em, Equipo.class).isEmpty()) {
            equipo = EquipoResourceIT.createEntity(em);
            em.persist(equipo);
            em.flush();
        } else {
            equipo = TestUtil.findAll(em, Equipo.class).get(0);
        }
        equiposinstalados.setEquipo(equipo);
        // Add required entity
        Proyecto proyecto;
        if (TestUtil.findAll(em, Proyecto.class).isEmpty()) {
            proyecto = ProyectoResourceIT.createEntity(em);
            em.persist(proyecto);
            em.flush();
        } else {
            proyecto = TestUtil.findAll(em, Proyecto.class).get(0);
        }
        equiposinstalados.setProyecto(proyecto);
        return equiposinstalados;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equiposinstalados createUpdatedEntity(EntityManager em) {
        Equiposinstalados equiposinstalados = new Equiposinstalados()
            .descripcion(UPDATED_DESCRIPCION)
            .observacion(UPDATED_OBSERVACION)
            .fechainstalacion(UPDATED_FECHAINSTALACION)
            .posibleactuliazcion(UPDATED_POSIBLEACTULIAZCION)
            .cantidad(UPDATED_CANTIDAD);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createUpdatedEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        equiposinstalados.setCliente(cliente);
        // Add required entity
        Equipo equipo;
        if (TestUtil.findAll(em, Equipo.class).isEmpty()) {
            equipo = EquipoResourceIT.createUpdatedEntity(em);
            em.persist(equipo);
            em.flush();
        } else {
            equipo = TestUtil.findAll(em, Equipo.class).get(0);
        }
        equiposinstalados.setEquipo(equipo);
        // Add required entity
        Proyecto proyecto;
        if (TestUtil.findAll(em, Proyecto.class).isEmpty()) {
            proyecto = ProyectoResourceIT.createUpdatedEntity(em);
            em.persist(proyecto);
            em.flush();
        } else {
            proyecto = TestUtil.findAll(em, Proyecto.class).get(0);
        }
        equiposinstalados.setProyecto(proyecto);
        return equiposinstalados;
    }

    @BeforeEach
    public void initTest() {
        equiposinstalados = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquiposinstalados() throws Exception {
        int databaseSizeBeforeCreate = equiposinstaladosRepository.findAll().size();

        // Create the Equiposinstalados
        restEquiposinstaladosMockMvc.perform(post("/api/equiposinstalados")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposinstalados)))
            .andExpect(status().isCreated());

        // Validate the Equiposinstalados in the database
        List<Equiposinstalados> equiposinstaladosList = equiposinstaladosRepository.findAll();
        assertThat(equiposinstaladosList).hasSize(databaseSizeBeforeCreate + 1);
        Equiposinstalados testEquiposinstalados = equiposinstaladosList.get(equiposinstaladosList.size() - 1);
        assertThat(testEquiposinstalados.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEquiposinstalados.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
        assertThat(testEquiposinstalados.getFechainstalacion()).isEqualTo(DEFAULT_FECHAINSTALACION);
        assertThat(testEquiposinstalados.isPosibleactuliazcion()).isEqualTo(DEFAULT_POSIBLEACTULIAZCION);
        assertThat(testEquiposinstalados.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createEquiposinstaladosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equiposinstaladosRepository.findAll().size();

        // Create the Equiposinstalados with an existing ID
        equiposinstalados.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquiposinstaladosMockMvc.perform(post("/api/equiposinstalados")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposinstalados)))
            .andExpect(status().isBadRequest());

        // Validate the Equiposinstalados in the database
        List<Equiposinstalados> equiposinstaladosList = equiposinstaladosRepository.findAll();
        assertThat(equiposinstaladosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = equiposinstaladosRepository.findAll().size();
        // set the field null
        equiposinstalados.setDescripcion(null);

        // Create the Equiposinstalados, which fails.

        restEquiposinstaladosMockMvc.perform(post("/api/equiposinstalados")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposinstalados)))
            .andExpect(status().isBadRequest());

        List<Equiposinstalados> equiposinstaladosList = equiposinstaladosRepository.findAll();
        assertThat(equiposinstaladosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObservacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = equiposinstaladosRepository.findAll().size();
        // set the field null
        equiposinstalados.setObservacion(null);

        // Create the Equiposinstalados, which fails.

        restEquiposinstaladosMockMvc.perform(post("/api/equiposinstalados")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposinstalados)))
            .andExpect(status().isBadRequest());

        List<Equiposinstalados> equiposinstaladosList = equiposinstaladosRepository.findAll();
        assertThat(equiposinstaladosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = equiposinstaladosRepository.findAll().size();
        // set the field null
        equiposinstalados.setCantidad(null);

        // Create the Equiposinstalados, which fails.

        restEquiposinstaladosMockMvc.perform(post("/api/equiposinstalados")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposinstalados)))
            .andExpect(status().isBadRequest());

        List<Equiposinstalados> equiposinstaladosList = equiposinstaladosRepository.findAll();
        assertThat(equiposinstaladosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquiposinstalados() throws Exception {
        // Initialize the database
        equiposinstaladosRepository.saveAndFlush(equiposinstalados);

        // Get all the equiposinstaladosList
        restEquiposinstaladosMockMvc.perform(get("/api/equiposinstalados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equiposinstalados.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION)))
            .andExpect(jsonPath("$.[*].fechainstalacion").value(hasItem(DEFAULT_FECHAINSTALACION.toString())))
            .andExpect(jsonPath("$.[*].posibleactuliazcion").value(hasItem(DEFAULT_POSIBLEACTULIAZCION.booleanValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())));
    }
    
    @Test
    @Transactional
    public void getEquiposinstalados() throws Exception {
        // Initialize the database
        equiposinstaladosRepository.saveAndFlush(equiposinstalados);

        // Get the equiposinstalados
        restEquiposinstaladosMockMvc.perform(get("/api/equiposinstalados/{id}", equiposinstalados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equiposinstalados.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION))
            .andExpect(jsonPath("$.fechainstalacion").value(DEFAULT_FECHAINSTALACION.toString()))
            .andExpect(jsonPath("$.posibleactuliazcion").value(DEFAULT_POSIBLEACTULIAZCION.booleanValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEquiposinstalados() throws Exception {
        // Get the equiposinstalados
        restEquiposinstaladosMockMvc.perform(get("/api/equiposinstalados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquiposinstalados() throws Exception {
        // Initialize the database
        equiposinstaladosRepository.saveAndFlush(equiposinstalados);

        int databaseSizeBeforeUpdate = equiposinstaladosRepository.findAll().size();

        // Update the equiposinstalados
        Equiposinstalados updatedEquiposinstalados = equiposinstaladosRepository.findById(equiposinstalados.getId()).get();
        // Disconnect from session so that the updates on updatedEquiposinstalados are not directly saved in db
        em.detach(updatedEquiposinstalados);
        updatedEquiposinstalados
            .descripcion(UPDATED_DESCRIPCION)
            .observacion(UPDATED_OBSERVACION)
            .fechainstalacion(UPDATED_FECHAINSTALACION)
            .posibleactuliazcion(UPDATED_POSIBLEACTULIAZCION)
            .cantidad(UPDATED_CANTIDAD);

        restEquiposinstaladosMockMvc.perform(put("/api/equiposinstalados")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquiposinstalados)))
            .andExpect(status().isOk());

        // Validate the Equiposinstalados in the database
        List<Equiposinstalados> equiposinstaladosList = equiposinstaladosRepository.findAll();
        assertThat(equiposinstaladosList).hasSize(databaseSizeBeforeUpdate);
        Equiposinstalados testEquiposinstalados = equiposinstaladosList.get(equiposinstaladosList.size() - 1);
        assertThat(testEquiposinstalados.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEquiposinstalados.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testEquiposinstalados.getFechainstalacion()).isEqualTo(UPDATED_FECHAINSTALACION);
        assertThat(testEquiposinstalados.isPosibleactuliazcion()).isEqualTo(UPDATED_POSIBLEACTULIAZCION);
        assertThat(testEquiposinstalados.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingEquiposinstalados() throws Exception {
        int databaseSizeBeforeUpdate = equiposinstaladosRepository.findAll().size();

        // Create the Equiposinstalados

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquiposinstaladosMockMvc.perform(put("/api/equiposinstalados")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiposinstalados)))
            .andExpect(status().isBadRequest());

        // Validate the Equiposinstalados in the database
        List<Equiposinstalados> equiposinstaladosList = equiposinstaladosRepository.findAll();
        assertThat(equiposinstaladosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquiposinstalados() throws Exception {
        // Initialize the database
        equiposinstaladosRepository.saveAndFlush(equiposinstalados);

        int databaseSizeBeforeDelete = equiposinstaladosRepository.findAll().size();

        // Delete the equiposinstalados
        restEquiposinstaladosMockMvc.perform(delete("/api/equiposinstalados/{id}", equiposinstalados.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equiposinstalados> equiposinstaladosList = equiposinstaladosRepository.findAll();
        assertThat(equiposinstaladosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
