package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.InteresesToCliente;
import co.com.hometechclaim.domain.Cliente;
import co.com.hometechclaim.domain.Intereses;
import co.com.hometechclaim.repository.InteresesToClienteRepository;
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
 * Integration tests for the {@link InteresesToClienteResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class InteresesToClienteResourceIT {

    private static final Instant DEFAULT_FECHACREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHACREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InteresesToClienteRepository interesesToClienteRepository;

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

    private MockMvc restInteresesToClienteMockMvc;

    private InteresesToCliente interesesToCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InteresesToClienteResource interesesToClienteResource = new InteresesToClienteResource(interesesToClienteRepository);
        this.restInteresesToClienteMockMvc = MockMvcBuilders.standaloneSetup(interesesToClienteResource)
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
    public static InteresesToCliente createEntity(EntityManager em) {
        InteresesToCliente interesesToCliente = new InteresesToCliente()
            .fechacreacion(DEFAULT_FECHACREACION);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        interesesToCliente.setCliente(cliente);
        // Add required entity
        Intereses intereses;
        if (TestUtil.findAll(em, Intereses.class).isEmpty()) {
            intereses = InteresesResourceIT.createEntity(em);
            em.persist(intereses);
            em.flush();
        } else {
            intereses = TestUtil.findAll(em, Intereses.class).get(0);
        }
        interesesToCliente.setIntereses(intereses);
        return interesesToCliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InteresesToCliente createUpdatedEntity(EntityManager em) {
        InteresesToCliente interesesToCliente = new InteresesToCliente()
            .fechacreacion(UPDATED_FECHACREACION);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createUpdatedEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        interesesToCliente.setCliente(cliente);
        // Add required entity
        Intereses intereses;
        if (TestUtil.findAll(em, Intereses.class).isEmpty()) {
            intereses = InteresesResourceIT.createUpdatedEntity(em);
            em.persist(intereses);
            em.flush();
        } else {
            intereses = TestUtil.findAll(em, Intereses.class).get(0);
        }
        interesesToCliente.setIntereses(intereses);
        return interesesToCliente;
    }

    @BeforeEach
    public void initTest() {
        interesesToCliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createInteresesToCliente() throws Exception {
        int databaseSizeBeforeCreate = interesesToClienteRepository.findAll().size();

        // Create the InteresesToCliente
        restInteresesToClienteMockMvc.perform(post("/api/intereses-to-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interesesToCliente)))
            .andExpect(status().isCreated());

        // Validate the InteresesToCliente in the database
        List<InteresesToCliente> interesesToClienteList = interesesToClienteRepository.findAll();
        assertThat(interesesToClienteList).hasSize(databaseSizeBeforeCreate + 1);
        InteresesToCliente testInteresesToCliente = interesesToClienteList.get(interesesToClienteList.size() - 1);
        assertThat(testInteresesToCliente.getFechacreacion()).isEqualTo(DEFAULT_FECHACREACION);
    }

    @Test
    @Transactional
    public void createInteresesToClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interesesToClienteRepository.findAll().size();

        // Create the InteresesToCliente with an existing ID
        interesesToCliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInteresesToClienteMockMvc.perform(post("/api/intereses-to-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interesesToCliente)))
            .andExpect(status().isBadRequest());

        // Validate the InteresesToCliente in the database
        List<InteresesToCliente> interesesToClienteList = interesesToClienteRepository.findAll();
        assertThat(interesesToClienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInteresesToClientes() throws Exception {
        // Initialize the database
        interesesToClienteRepository.saveAndFlush(interesesToCliente);

        // Get all the interesesToClienteList
        restInteresesToClienteMockMvc.perform(get("/api/intereses-to-clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interesesToCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechacreacion").value(hasItem(DEFAULT_FECHACREACION.toString())));
    }
    
    @Test
    @Transactional
    public void getInteresesToCliente() throws Exception {
        // Initialize the database
        interesesToClienteRepository.saveAndFlush(interesesToCliente);

        // Get the interesesToCliente
        restInteresesToClienteMockMvc.perform(get("/api/intereses-to-clientes/{id}", interesesToCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interesesToCliente.getId().intValue()))
            .andExpect(jsonPath("$.fechacreacion").value(DEFAULT_FECHACREACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInteresesToCliente() throws Exception {
        // Get the interesesToCliente
        restInteresesToClienteMockMvc.perform(get("/api/intereses-to-clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInteresesToCliente() throws Exception {
        // Initialize the database
        interesesToClienteRepository.saveAndFlush(interesesToCliente);

        int databaseSizeBeforeUpdate = interesesToClienteRepository.findAll().size();

        // Update the interesesToCliente
        InteresesToCliente updatedInteresesToCliente = interesesToClienteRepository.findById(interesesToCliente.getId()).get();
        // Disconnect from session so that the updates on updatedInteresesToCliente are not directly saved in db
        em.detach(updatedInteresesToCliente);
        updatedInteresesToCliente
            .fechacreacion(UPDATED_FECHACREACION);

        restInteresesToClienteMockMvc.perform(put("/api/intereses-to-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInteresesToCliente)))
            .andExpect(status().isOk());

        // Validate the InteresesToCliente in the database
        List<InteresesToCliente> interesesToClienteList = interesesToClienteRepository.findAll();
        assertThat(interesesToClienteList).hasSize(databaseSizeBeforeUpdate);
        InteresesToCliente testInteresesToCliente = interesesToClienteList.get(interesesToClienteList.size() - 1);
        assertThat(testInteresesToCliente.getFechacreacion()).isEqualTo(UPDATED_FECHACREACION);
    }

    @Test
    @Transactional
    public void updateNonExistingInteresesToCliente() throws Exception {
        int databaseSizeBeforeUpdate = interesesToClienteRepository.findAll().size();

        // Create the InteresesToCliente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInteresesToClienteMockMvc.perform(put("/api/intereses-to-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interesesToCliente)))
            .andExpect(status().isBadRequest());

        // Validate the InteresesToCliente in the database
        List<InteresesToCliente> interesesToClienteList = interesesToClienteRepository.findAll();
        assertThat(interesesToClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInteresesToCliente() throws Exception {
        // Initialize the database
        interesesToClienteRepository.saveAndFlush(interesesToCliente);

        int databaseSizeBeforeDelete = interesesToClienteRepository.findAll().size();

        // Delete the interesesToCliente
        restInteresesToClienteMockMvc.perform(delete("/api/intereses-to-clientes/{id}", interesesToCliente.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InteresesToCliente> interesesToClienteList = interesesToClienteRepository.findAll();
        assertThat(interesesToClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
