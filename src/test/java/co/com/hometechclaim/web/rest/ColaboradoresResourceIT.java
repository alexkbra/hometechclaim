package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Colaboradores;
import co.com.hometechclaim.domain.Dealer;
import co.com.hometechclaim.repository.ColaboradoresRepository;
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
 * Integration tests for the {@link ColaboradoresResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class ColaboradoresResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String DEFAULT_IDUSUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDUSUARIO = "BBBBBBBBBB";

    @Autowired
    private ColaboradoresRepository colaboradoresRepository;

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

    private MockMvc restColaboradoresMockMvc;

    private Colaboradores colaboradores;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColaboradoresResource colaboradoresResource = new ColaboradoresResource(colaboradoresRepository);
        this.restColaboradoresMockMvc = MockMvcBuilders.standaloneSetup(colaboradoresResource)
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
    public static Colaboradores createEntity(EntityManager em) {
        Colaboradores colaboradores = new Colaboradores()
            .nombre(DEFAULT_NOMBRE)
            .correo(DEFAULT_CORREO)
            .activo(DEFAULT_ACTIVO)
            .idusuario(DEFAULT_IDUSUARIO);
        // Add required entity
        Dealer dealer;
        if (TestUtil.findAll(em, Dealer.class).isEmpty()) {
            dealer = DealerResourceIT.createEntity(em);
            em.persist(dealer);
            em.flush();
        } else {
            dealer = TestUtil.findAll(em, Dealer.class).get(0);
        }
        colaboradores.setDealer(dealer);
        return colaboradores;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colaboradores createUpdatedEntity(EntityManager em) {
        Colaboradores colaboradores = new Colaboradores()
            .nombre(UPDATED_NOMBRE)
            .correo(UPDATED_CORREO)
            .activo(UPDATED_ACTIVO)
            .idusuario(UPDATED_IDUSUARIO);
        // Add required entity
        Dealer dealer;
        if (TestUtil.findAll(em, Dealer.class).isEmpty()) {
            dealer = DealerResourceIT.createUpdatedEntity(em);
            em.persist(dealer);
            em.flush();
        } else {
            dealer = TestUtil.findAll(em, Dealer.class).get(0);
        }
        colaboradores.setDealer(dealer);
        return colaboradores;
    }

    @BeforeEach
    public void initTest() {
        colaboradores = createEntity(em);
    }

    @Test
    @Transactional
    public void createColaboradores() throws Exception {
        int databaseSizeBeforeCreate = colaboradoresRepository.findAll().size();

        // Create the Colaboradores
        restColaboradoresMockMvc.perform(post("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isCreated());

        // Validate the Colaboradores in the database
        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeCreate + 1);
        Colaboradores testColaboradores = colaboradoresList.get(colaboradoresList.size() - 1);
        assertThat(testColaboradores.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testColaboradores.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testColaboradores.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testColaboradores.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
    }

    @Test
    @Transactional
    public void createColaboradoresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = colaboradoresRepository.findAll().size();

        // Create the Colaboradores with an existing ID
        colaboradores.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColaboradoresMockMvc.perform(post("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isBadRequest());

        // Validate the Colaboradores in the database
        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = colaboradoresRepository.findAll().size();
        // set the field null
        colaboradores.setNombre(null);

        // Create the Colaboradores, which fails.

        restColaboradoresMockMvc.perform(post("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isBadRequest());

        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllColaboradores() throws Exception {
        // Initialize the database
        colaboradoresRepository.saveAndFlush(colaboradores);

        // Get all the colaboradoresList
        restColaboradoresMockMvc.perform(get("/api/colaboradores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colaboradores.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO)));
    }
    
    @Test
    @Transactional
    public void getColaboradores() throws Exception {
        // Initialize the database
        colaboradoresRepository.saveAndFlush(colaboradores);

        // Get the colaboradores
        restColaboradoresMockMvc.perform(get("/api/colaboradores/{id}", colaboradores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(colaboradores.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO));
    }

    @Test
    @Transactional
    public void getNonExistingColaboradores() throws Exception {
        // Get the colaboradores
        restColaboradoresMockMvc.perform(get("/api/colaboradores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColaboradores() throws Exception {
        // Initialize the database
        colaboradoresRepository.saveAndFlush(colaboradores);

        int databaseSizeBeforeUpdate = colaboradoresRepository.findAll().size();

        // Update the colaboradores
        Colaboradores updatedColaboradores = colaboradoresRepository.findById(colaboradores.getId()).get();
        // Disconnect from session so that the updates on updatedColaboradores are not directly saved in db
        em.detach(updatedColaboradores);
        updatedColaboradores
            .nombre(UPDATED_NOMBRE)
            .correo(UPDATED_CORREO)
            .activo(UPDATED_ACTIVO)
            .idusuario(UPDATED_IDUSUARIO);

        restColaboradoresMockMvc.perform(put("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedColaboradores)))
            .andExpect(status().isOk());

        // Validate the Colaboradores in the database
        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeUpdate);
        Colaboradores testColaboradores = colaboradoresList.get(colaboradoresList.size() - 1);
        assertThat(testColaboradores.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testColaboradores.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testColaboradores.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testColaboradores.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingColaboradores() throws Exception {
        int databaseSizeBeforeUpdate = colaboradoresRepository.findAll().size();

        // Create the Colaboradores

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColaboradoresMockMvc.perform(put("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isBadRequest());

        // Validate the Colaboradores in the database
        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteColaboradores() throws Exception {
        // Initialize the database
        colaboradoresRepository.saveAndFlush(colaboradores);

        int databaseSizeBeforeDelete = colaboradoresRepository.findAll().size();

        // Delete the colaboradores
        restColaboradoresMockMvc.perform(delete("/api/colaboradores/{id}", colaboradores.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
