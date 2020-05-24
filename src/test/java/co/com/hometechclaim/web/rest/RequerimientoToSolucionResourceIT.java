package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.RequerimientoToSolucion;
import co.com.hometechclaim.domain.Requerimiento;
import co.com.hometechclaim.domain.Solucion;
import co.com.hometechclaim.repository.RequerimientoToSolucionRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static co.com.hometechclaim.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RequerimientoToSolucionResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class RequerimientoToSolucionResourceIT {

    private static final Instant DEFAULT_FECHACREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHACREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RequerimientoToSolucionRepository requerimientoToSolucionRepository;

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

    private MockMvc restRequerimientoToSolucionMockMvc;

    private RequerimientoToSolucion requerimientoToSolucion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequerimientoToSolucionResource requerimientoToSolucionResource = new RequerimientoToSolucionResource(requerimientoToSolucionRepository);
        this.restRequerimientoToSolucionMockMvc = MockMvcBuilders.standaloneSetup(requerimientoToSolucionResource)
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
    public static RequerimientoToSolucion createEntity(EntityManager em) {
        RequerimientoToSolucion requerimientoToSolucion = new RequerimientoToSolucion()
            .fechacreacion(DEFAULT_FECHACREACION);
        // Add required entity
        Requerimiento requerimiento;
        if (TestUtil.findAll(em, Requerimiento.class).isEmpty()) {
            requerimiento = RequerimientoResourceIT.createEntity(em);
            em.persist(requerimiento);
            em.flush();
        } else {
            requerimiento = TestUtil.findAll(em, Requerimiento.class).get(0);
        }
        requerimientoToSolucion.setRequerimiento(requerimiento);
        // Add required entity
        Solucion solucion;
        if (TestUtil.findAll(em, Solucion.class).isEmpty()) {
            solucion = SolucionResourceIT.createEntity(em);
            em.persist(solucion);
            em.flush();
        } else {
            solucion = TestUtil.findAll(em, Solucion.class).get(0);
        }
        requerimientoToSolucion.setSolucion(solucion);
        return requerimientoToSolucion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequerimientoToSolucion createUpdatedEntity(EntityManager em) {
        RequerimientoToSolucion requerimientoToSolucion = new RequerimientoToSolucion()
            .fechacreacion(UPDATED_FECHACREACION);
        // Add required entity
        Requerimiento requerimiento;
        if (TestUtil.findAll(em, Requerimiento.class).isEmpty()) {
            requerimiento = RequerimientoResourceIT.createUpdatedEntity(em);
            em.persist(requerimiento);
            em.flush();
        } else {
            requerimiento = TestUtil.findAll(em, Requerimiento.class).get(0);
        }
        requerimientoToSolucion.setRequerimiento(requerimiento);
        // Add required entity
        Solucion solucion;
        if (TestUtil.findAll(em, Solucion.class).isEmpty()) {
            solucion = SolucionResourceIT.createUpdatedEntity(em);
            em.persist(solucion);
            em.flush();
        } else {
            solucion = TestUtil.findAll(em, Solucion.class).get(0);
        }
        requerimientoToSolucion.setSolucion(solucion);
        return requerimientoToSolucion;
    }

    @BeforeEach
    public void initTest() {
        requerimientoToSolucion = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequerimientoToSolucion() throws Exception {
        int databaseSizeBeforeCreate = requerimientoToSolucionRepository.findAll().size();

        // Create the RequerimientoToSolucion
        restRequerimientoToSolucionMockMvc.perform(post("/api/requerimiento-to-solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requerimientoToSolucion)))
            .andExpect(status().isCreated());

        // Validate the RequerimientoToSolucion in the database
        List<RequerimientoToSolucion> requerimientoToSolucionList = requerimientoToSolucionRepository.findAll();
        assertThat(requerimientoToSolucionList).hasSize(databaseSizeBeforeCreate + 1);
        RequerimientoToSolucion testRequerimientoToSolucion = requerimientoToSolucionList.get(requerimientoToSolucionList.size() - 1);
        assertThat(testRequerimientoToSolucion.getFechacreacion()).isEqualTo(DEFAULT_FECHACREACION);
    }

    @Test
    @Transactional
    public void createRequerimientoToSolucionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requerimientoToSolucionRepository.findAll().size();

        // Create the RequerimientoToSolucion with an existing ID
        requerimientoToSolucion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequerimientoToSolucionMockMvc.perform(post("/api/requerimiento-to-solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requerimientoToSolucion)))
            .andExpect(status().isBadRequest());

        // Validate the RequerimientoToSolucion in the database
        List<RequerimientoToSolucion> requerimientoToSolucionList = requerimientoToSolucionRepository.findAll();
        assertThat(requerimientoToSolucionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechacreacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = requerimientoToSolucionRepository.findAll().size();
        // set the field null
        requerimientoToSolucion.setFechacreacion(null);

        // Create the RequerimientoToSolucion, which fails.

        restRequerimientoToSolucionMockMvc.perform(post("/api/requerimiento-to-solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requerimientoToSolucion)))
            .andExpect(status().isBadRequest());

        List<RequerimientoToSolucion> requerimientoToSolucionList = requerimientoToSolucionRepository.findAll();
        assertThat(requerimientoToSolucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequerimientoToSolucions() throws Exception {
        // Initialize the database
        requerimientoToSolucionRepository.saveAndFlush(requerimientoToSolucion);

        // Get all the requerimientoToSolucionList
        restRequerimientoToSolucionMockMvc.perform(get("/api/requerimiento-to-solucions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requerimientoToSolucion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechacreacion").value(hasItem(DEFAULT_FECHACREACION.toString())));
    }
    
    @Test
    @Transactional
    public void getRequerimientoToSolucion() throws Exception {
        // Initialize the database
        requerimientoToSolucionRepository.saveAndFlush(requerimientoToSolucion);

        // Get the requerimientoToSolucion
        restRequerimientoToSolucionMockMvc.perform(get("/api/requerimiento-to-solucions/{id}", requerimientoToSolucion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requerimientoToSolucion.getId().intValue()))
            .andExpect(jsonPath("$.fechacreacion").value(DEFAULT_FECHACREACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequerimientoToSolucion() throws Exception {
        // Get the requerimientoToSolucion
        restRequerimientoToSolucionMockMvc.perform(get("/api/requerimiento-to-solucions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequerimientoToSolucion() throws Exception {
        // Initialize the database
        requerimientoToSolucionRepository.saveAndFlush(requerimientoToSolucion);

        int databaseSizeBeforeUpdate = requerimientoToSolucionRepository.findAll().size();

        // Update the requerimientoToSolucion
        RequerimientoToSolucion updatedRequerimientoToSolucion = requerimientoToSolucionRepository.findById(requerimientoToSolucion.getId()).get();
        // Disconnect from session so that the updates on updatedRequerimientoToSolucion are not directly saved in db
        em.detach(updatedRequerimientoToSolucion);
        updatedRequerimientoToSolucion
            .fechacreacion(UPDATED_FECHACREACION);

        restRequerimientoToSolucionMockMvc.perform(put("/api/requerimiento-to-solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequerimientoToSolucion)))
            .andExpect(status().isOk());

        // Validate the RequerimientoToSolucion in the database
        List<RequerimientoToSolucion> requerimientoToSolucionList = requerimientoToSolucionRepository.findAll();
        assertThat(requerimientoToSolucionList).hasSize(databaseSizeBeforeUpdate);
        RequerimientoToSolucion testRequerimientoToSolucion = requerimientoToSolucionList.get(requerimientoToSolucionList.size() - 1);
        assertThat(testRequerimientoToSolucion.getFechacreacion()).isEqualTo(UPDATED_FECHACREACION);
    }

    @Test
    @Transactional
    public void updateNonExistingRequerimientoToSolucion() throws Exception {
        int databaseSizeBeforeUpdate = requerimientoToSolucionRepository.findAll().size();

        // Create the RequerimientoToSolucion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequerimientoToSolucionMockMvc.perform(put("/api/requerimiento-to-solucions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requerimientoToSolucion)))
            .andExpect(status().isBadRequest());

        // Validate the RequerimientoToSolucion in the database
        List<RequerimientoToSolucion> requerimientoToSolucionList = requerimientoToSolucionRepository.findAll();
        assertThat(requerimientoToSolucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRequerimientoToSolucion() throws Exception {
        // Initialize the database
        requerimientoToSolucionRepository.saveAndFlush(requerimientoToSolucion);

        int databaseSizeBeforeDelete = requerimientoToSolucionRepository.findAll().size();

        // Delete the requerimientoToSolucion
        restRequerimientoToSolucionMockMvc.perform(delete("/api/requerimiento-to-solucions/{id}", requerimientoToSolucion.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequerimientoToSolucion> requerimientoToSolucionList = requerimientoToSolucionRepository.findAll();
        assertThat(requerimientoToSolucionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
