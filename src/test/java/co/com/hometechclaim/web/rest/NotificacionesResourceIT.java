package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Notificaciones;
import co.com.hometechclaim.repository.NotificacionesRepository;
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

import co.com.hometechclaim.domain.enumeration.Areas;
/**
 * Integration tests for the {@link NotificacionesResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class NotificacionesResourceIT {

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final Areas DEFAULT_AREA = Areas.SRVICIOALCLIENTE;
    private static final Areas UPDATED_AREA = Areas.AREATECNICA;

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String DEFAULT_IDUSUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDUSUARIO = "BBBBBBBBBB";

    @Autowired
    private NotificacionesRepository notificacionesRepository;

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

    private MockMvc restNotificacionesMockMvc;

    private Notificaciones notificaciones;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotificacionesResource notificacionesResource = new NotificacionesResource(notificacionesRepository);
        this.restNotificacionesMockMvc = MockMvcBuilders.standaloneSetup(notificacionesResource)
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
    public static Notificaciones createEntity(EntityManager em) {
        Notificaciones notificaciones = new Notificaciones()
            .correo(DEFAULT_CORREO)
            .area(DEFAULT_AREA)
            .activo(DEFAULT_ACTIVO)
            .idusuario(DEFAULT_IDUSUARIO);
        return notificaciones;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notificaciones createUpdatedEntity(EntityManager em) {
        Notificaciones notificaciones = new Notificaciones()
            .correo(UPDATED_CORREO)
            .area(UPDATED_AREA)
            .activo(UPDATED_ACTIVO)
            .idusuario(UPDATED_IDUSUARIO);
        return notificaciones;
    }

    @BeforeEach
    public void initTest() {
        notificaciones = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificaciones() throws Exception {
        int databaseSizeBeforeCreate = notificacionesRepository.findAll().size();

        // Create the Notificaciones
        restNotificacionesMockMvc.perform(post("/api/notificaciones")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificaciones)))
            .andExpect(status().isCreated());

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeCreate + 1);
        Notificaciones testNotificaciones = notificacionesList.get(notificacionesList.size() - 1);
        assertThat(testNotificaciones.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testNotificaciones.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testNotificaciones.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testNotificaciones.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
    }

    @Test
    @Transactional
    public void createNotificacionesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificacionesRepository.findAll().size();

        // Create the Notificaciones with an existing ID
        notificaciones.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificacionesMockMvc.perform(post("/api/notificaciones")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificaciones)))
            .andExpect(status().isBadRequest());

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCorreoIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionesRepository.findAll().size();
        // set the field null
        notificaciones.setCorreo(null);

        // Create the Notificaciones, which fails.

        restNotificacionesMockMvc.perform(post("/api/notificaciones")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificaciones)))
            .andExpect(status().isBadRequest());

        List<Notificaciones> notificacionesList = notificacionesRepository.findAll();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionesRepository.findAll().size();
        // set the field null
        notificaciones.setArea(null);

        // Create the Notificaciones, which fails.

        restNotificacionesMockMvc.perform(post("/api/notificaciones")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificaciones)))
            .andExpect(status().isBadRequest());

        List<Notificaciones> notificacionesList = notificacionesRepository.findAll();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacionesRepository.findAll().size();
        // set the field null
        notificaciones.setActivo(null);

        // Create the Notificaciones, which fails.

        restNotificacionesMockMvc.perform(post("/api/notificaciones")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificaciones)))
            .andExpect(status().isBadRequest());

        List<Notificaciones> notificacionesList = notificacionesRepository.findAll();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotificaciones() throws Exception {
        // Initialize the database
        notificacionesRepository.saveAndFlush(notificaciones);

        // Get all the notificacionesList
        restNotificacionesMockMvc.perform(get("/api/notificaciones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificaciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO)));
    }
    
    @Test
    @Transactional
    public void getNotificaciones() throws Exception {
        // Initialize the database
        notificacionesRepository.saveAndFlush(notificaciones);

        // Get the notificaciones
        restNotificacionesMockMvc.perform(get("/api/notificaciones/{id}", notificaciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificaciones.getId().intValue()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO));
    }

    @Test
    @Transactional
    public void getNonExistingNotificaciones() throws Exception {
        // Get the notificaciones
        restNotificacionesMockMvc.perform(get("/api/notificaciones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificaciones() throws Exception {
        // Initialize the database
        notificacionesRepository.saveAndFlush(notificaciones);

        int databaseSizeBeforeUpdate = notificacionesRepository.findAll().size();

        // Update the notificaciones
        Notificaciones updatedNotificaciones = notificacionesRepository.findById(notificaciones.getId()).get();
        // Disconnect from session so that the updates on updatedNotificaciones are not directly saved in db
        em.detach(updatedNotificaciones);
        updatedNotificaciones
            .correo(UPDATED_CORREO)
            .area(UPDATED_AREA)
            .activo(UPDATED_ACTIVO)
            .idusuario(UPDATED_IDUSUARIO);

        restNotificacionesMockMvc.perform(put("/api/notificaciones")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotificaciones)))
            .andExpect(status().isOk());

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeUpdate);
        Notificaciones testNotificaciones = notificacionesList.get(notificacionesList.size() - 1);
        assertThat(testNotificaciones.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testNotificaciones.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testNotificaciones.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testNotificaciones.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificaciones() throws Exception {
        int databaseSizeBeforeUpdate = notificacionesRepository.findAll().size();

        // Create the Notificaciones

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificacionesMockMvc.perform(put("/api/notificaciones")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificaciones)))
            .andExpect(status().isBadRequest());

        // Validate the Notificaciones in the database
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotificaciones() throws Exception {
        // Initialize the database
        notificacionesRepository.saveAndFlush(notificaciones);

        int databaseSizeBeforeDelete = notificacionesRepository.findAll().size();

        // Delete the notificaciones
        restNotificacionesMockMvc.perform(delete("/api/notificaciones/{id}", notificaciones.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notificaciones> notificacionesList = notificacionesRepository.findAll();
        assertThat(notificacionesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
