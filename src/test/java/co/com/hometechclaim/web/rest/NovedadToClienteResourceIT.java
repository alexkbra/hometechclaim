package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.NovedadToCliente;
import co.com.hometechclaim.domain.Novedad;
import co.com.hometechclaim.domain.Cliente;
import co.com.hometechclaim.repository.NovedadToClienteRepository;
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
 * Integration tests for the {@link NovedadToClienteResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class NovedadToClienteResourceIT {

    private static final Instant DEFAULT_FECHACREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHACREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NovedadToClienteRepository novedadToClienteRepository;

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

    private MockMvc restNovedadToClienteMockMvc;

    private NovedadToCliente novedadToCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NovedadToClienteResource novedadToClienteResource = new NovedadToClienteResource(novedadToClienteRepository);
        this.restNovedadToClienteMockMvc = MockMvcBuilders.standaloneSetup(novedadToClienteResource)
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
    public static NovedadToCliente createEntity(EntityManager em) {
        NovedadToCliente novedadToCliente = new NovedadToCliente()
            .fechacreacion(DEFAULT_FECHACREACION);
        // Add required entity
        Novedad novedad;
        if (TestUtil.findAll(em, Novedad.class).isEmpty()) {
            novedad = NovedadResourceIT.createEntity(em);
            em.persist(novedad);
            em.flush();
        } else {
            novedad = TestUtil.findAll(em, Novedad.class).get(0);
        }
        novedadToCliente.setNovedad(novedad);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        novedadToCliente.setCliente(cliente);
        return novedadToCliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NovedadToCliente createUpdatedEntity(EntityManager em) {
        NovedadToCliente novedadToCliente = new NovedadToCliente()
            .fechacreacion(UPDATED_FECHACREACION);
        // Add required entity
        Novedad novedad;
        if (TestUtil.findAll(em, Novedad.class).isEmpty()) {
            novedad = NovedadResourceIT.createUpdatedEntity(em);
            em.persist(novedad);
            em.flush();
        } else {
            novedad = TestUtil.findAll(em, Novedad.class).get(0);
        }
        novedadToCliente.setNovedad(novedad);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createUpdatedEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        novedadToCliente.setCliente(cliente);
        return novedadToCliente;
    }

    @BeforeEach
    public void initTest() {
        novedadToCliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createNovedadToCliente() throws Exception {
        int databaseSizeBeforeCreate = novedadToClienteRepository.findAll().size();

        // Create the NovedadToCliente
        restNovedadToClienteMockMvc.perform(post("/api/novedad-to-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(novedadToCliente)))
            .andExpect(status().isCreated());

        // Validate the NovedadToCliente in the database
        List<NovedadToCliente> novedadToClienteList = novedadToClienteRepository.findAll();
        assertThat(novedadToClienteList).hasSize(databaseSizeBeforeCreate + 1);
        NovedadToCliente testNovedadToCliente = novedadToClienteList.get(novedadToClienteList.size() - 1);
        assertThat(testNovedadToCliente.getFechacreacion()).isEqualTo(DEFAULT_FECHACREACION);
    }

    @Test
    @Transactional
    public void createNovedadToClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = novedadToClienteRepository.findAll().size();

        // Create the NovedadToCliente with an existing ID
        novedadToCliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNovedadToClienteMockMvc.perform(post("/api/novedad-to-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(novedadToCliente)))
            .andExpect(status().isBadRequest());

        // Validate the NovedadToCliente in the database
        List<NovedadToCliente> novedadToClienteList = novedadToClienteRepository.findAll();
        assertThat(novedadToClienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNovedadToClientes() throws Exception {
        // Initialize the database
        novedadToClienteRepository.saveAndFlush(novedadToCliente);

        // Get all the novedadToClienteList
        restNovedadToClienteMockMvc.perform(get("/api/novedad-to-clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(novedadToCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechacreacion").value(hasItem(DEFAULT_FECHACREACION.toString())));
    }
    
    @Test
    @Transactional
    public void getNovedadToCliente() throws Exception {
        // Initialize the database
        novedadToClienteRepository.saveAndFlush(novedadToCliente);

        // Get the novedadToCliente
        restNovedadToClienteMockMvc.perform(get("/api/novedad-to-clientes/{id}", novedadToCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(novedadToCliente.getId().intValue()))
            .andExpect(jsonPath("$.fechacreacion").value(DEFAULT_FECHACREACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNovedadToCliente() throws Exception {
        // Get the novedadToCliente
        restNovedadToClienteMockMvc.perform(get("/api/novedad-to-clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNovedadToCliente() throws Exception {
        // Initialize the database
        novedadToClienteRepository.saveAndFlush(novedadToCliente);

        int databaseSizeBeforeUpdate = novedadToClienteRepository.findAll().size();

        // Update the novedadToCliente
        NovedadToCliente updatedNovedadToCliente = novedadToClienteRepository.findById(novedadToCliente.getId()).get();
        // Disconnect from session so that the updates on updatedNovedadToCliente are not directly saved in db
        em.detach(updatedNovedadToCliente);
        updatedNovedadToCliente
            .fechacreacion(UPDATED_FECHACREACION);

        restNovedadToClienteMockMvc.perform(put("/api/novedad-to-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNovedadToCliente)))
            .andExpect(status().isOk());

        // Validate the NovedadToCliente in the database
        List<NovedadToCliente> novedadToClienteList = novedadToClienteRepository.findAll();
        assertThat(novedadToClienteList).hasSize(databaseSizeBeforeUpdate);
        NovedadToCliente testNovedadToCliente = novedadToClienteList.get(novedadToClienteList.size() - 1);
        assertThat(testNovedadToCliente.getFechacreacion()).isEqualTo(UPDATED_FECHACREACION);
    }

    @Test
    @Transactional
    public void updateNonExistingNovedadToCliente() throws Exception {
        int databaseSizeBeforeUpdate = novedadToClienteRepository.findAll().size();

        // Create the NovedadToCliente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNovedadToClienteMockMvc.perform(put("/api/novedad-to-clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(novedadToCliente)))
            .andExpect(status().isBadRequest());

        // Validate the NovedadToCliente in the database
        List<NovedadToCliente> novedadToClienteList = novedadToClienteRepository.findAll();
        assertThat(novedadToClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNovedadToCliente() throws Exception {
        // Initialize the database
        novedadToClienteRepository.saveAndFlush(novedadToCliente);

        int databaseSizeBeforeDelete = novedadToClienteRepository.findAll().size();

        // Delete the novedadToCliente
        restNovedadToClienteMockMvc.perform(delete("/api/novedad-to-clientes/{id}", novedadToCliente.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NovedadToCliente> novedadToClienteList = novedadToClienteRepository.findAll();
        assertThat(novedadToClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
