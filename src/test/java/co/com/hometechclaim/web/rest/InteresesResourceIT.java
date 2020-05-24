package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Intereses;
import co.com.hometechclaim.repository.InteresesRepository;
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
 * Integration tests for the {@link InteresesResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class InteresesResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private InteresesRepository interesesRepository;

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

    private MockMvc restInteresesMockMvc;

    private Intereses intereses;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InteresesResource interesesResource = new InteresesResource(interesesRepository);
        this.restInteresesMockMvc = MockMvcBuilders.standaloneSetup(interesesResource)
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
    public static Intereses createEntity(EntityManager em) {
        Intereses intereses = new Intereses()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .imagen(DEFAULT_IMAGEN)
            .code(DEFAULT_CODE);
        return intereses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Intereses createUpdatedEntity(EntityManager em) {
        Intereses intereses = new Intereses()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .code(UPDATED_CODE);
        return intereses;
    }

    @BeforeEach
    public void initTest() {
        intereses = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntereses() throws Exception {
        int databaseSizeBeforeCreate = interesesRepository.findAll().size();

        // Create the Intereses
        restInteresesMockMvc.perform(post("/api/intereses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intereses)))
            .andExpect(status().isCreated());

        // Validate the Intereses in the database
        List<Intereses> interesesList = interesesRepository.findAll();
        assertThat(interesesList).hasSize(databaseSizeBeforeCreate + 1);
        Intereses testIntereses = interesesList.get(interesesList.size() - 1);
        assertThat(testIntereses.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testIntereses.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testIntereses.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testIntereses.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createInteresesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interesesRepository.findAll().size();

        // Create the Intereses with an existing ID
        intereses.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInteresesMockMvc.perform(post("/api/intereses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intereses)))
            .andExpect(status().isBadRequest());

        // Validate the Intereses in the database
        List<Intereses> interesesList = interesesRepository.findAll();
        assertThat(interesesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = interesesRepository.findAll().size();
        // set the field null
        intereses.setNombre(null);

        // Create the Intereses, which fails.

        restInteresesMockMvc.perform(post("/api/intereses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intereses)))
            .andExpect(status().isBadRequest());

        List<Intereses> interesesList = interesesRepository.findAll();
        assertThat(interesesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = interesesRepository.findAll().size();
        // set the field null
        intereses.setDescripcion(null);

        // Create the Intereses, which fails.

        restInteresesMockMvc.perform(post("/api/intereses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intereses)))
            .andExpect(status().isBadRequest());

        List<Intereses> interesesList = interesesRepository.findAll();
        assertThat(interesesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImagenIsRequired() throws Exception {
        int databaseSizeBeforeTest = interesesRepository.findAll().size();
        // set the field null
        intereses.setImagen(null);

        // Create the Intereses, which fails.

        restInteresesMockMvc.perform(post("/api/intereses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intereses)))
            .andExpect(status().isBadRequest());

        List<Intereses> interesesList = interesesRepository.findAll();
        assertThat(interesesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = interesesRepository.findAll().size();
        // set the field null
        intereses.setCode(null);

        // Create the Intereses, which fails.

        restInteresesMockMvc.perform(post("/api/intereses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intereses)))
            .andExpect(status().isBadRequest());

        List<Intereses> interesesList = interesesRepository.findAll();
        assertThat(interesesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIntereses() throws Exception {
        // Initialize the database
        interesesRepository.saveAndFlush(intereses);

        // Get all the interesesList
        restInteresesMockMvc.perform(get("/api/intereses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intereses.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getIntereses() throws Exception {
        // Initialize the database
        interesesRepository.saveAndFlush(intereses);

        // Get the intereses
        restInteresesMockMvc.perform(get("/api/intereses/{id}", intereses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intereses.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.imagen").value(DEFAULT_IMAGEN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingIntereses() throws Exception {
        // Get the intereses
        restInteresesMockMvc.perform(get("/api/intereses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntereses() throws Exception {
        // Initialize the database
        interesesRepository.saveAndFlush(intereses);

        int databaseSizeBeforeUpdate = interesesRepository.findAll().size();

        // Update the intereses
        Intereses updatedIntereses = interesesRepository.findById(intereses.getId()).get();
        // Disconnect from session so that the updates on updatedIntereses are not directly saved in db
        em.detach(updatedIntereses);
        updatedIntereses
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .code(UPDATED_CODE);

        restInteresesMockMvc.perform(put("/api/intereses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIntereses)))
            .andExpect(status().isOk());

        // Validate the Intereses in the database
        List<Intereses> interesesList = interesesRepository.findAll();
        assertThat(interesesList).hasSize(databaseSizeBeforeUpdate);
        Intereses testIntereses = interesesList.get(interesesList.size() - 1);
        assertThat(testIntereses.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testIntereses.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testIntereses.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testIntereses.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingIntereses() throws Exception {
        int databaseSizeBeforeUpdate = interesesRepository.findAll().size();

        // Create the Intereses

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInteresesMockMvc.perform(put("/api/intereses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intereses)))
            .andExpect(status().isBadRequest());

        // Validate the Intereses in the database
        List<Intereses> interesesList = interesesRepository.findAll();
        assertThat(interesesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntereses() throws Exception {
        // Initialize the database
        interesesRepository.saveAndFlush(intereses);

        int databaseSizeBeforeDelete = interesesRepository.findAll().size();

        // Delete the intereses
        restInteresesMockMvc.perform(delete("/api/intereses/{id}", intereses.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Intereses> interesesList = interesesRepository.findAll();
        assertThat(interesesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
