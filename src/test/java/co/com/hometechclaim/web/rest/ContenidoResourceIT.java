package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Contenido;
import co.com.hometechclaim.domain.Novedad;
import co.com.hometechclaim.repository.ContenidoRepository;
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
import org.springframework.util.Base64Utils;
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

import co.com.hometechclaim.domain.enumeration.TipoContenido;
/**
 * Integration tests for the {@link ContenidoResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class ContenidoResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN_U_RL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_U_RL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_U_RL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_U_RL_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_AUDIO = "AAAAAAAAAA";
    private static final String UPDATED_AUDIO = "BBBBBBBBBB";

    private static final TipoContenido DEFAULT_TIPO_CONTENIDO = TipoContenido.VIDEO;
    private static final TipoContenido UPDATED_TIPO_CONTENIDO = TipoContenido.IMAGEN;

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final Instant DEFAULT_FECHACREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHACREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContenidoRepository contenidoRepository;

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

    private MockMvc restContenidoMockMvc;

    private Contenido contenido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContenidoResource contenidoResource = new ContenidoResource(contenidoRepository);
        this.restContenidoMockMvc = MockMvcBuilders.standaloneSetup(contenidoResource)
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
    public static Contenido createEntity(EntityManager em) {
        Contenido contenido = new Contenido()
            .descripcion(DEFAULT_DESCRIPCION)
            .videoUrl(DEFAULT_VIDEO_URL)
            .imagenURl(DEFAULT_IMAGEN_U_RL)
            .imagenURlContentType(DEFAULT_IMAGEN_U_RL_CONTENT_TYPE)
            .audio(DEFAULT_AUDIO)
            .tipoContenido(DEFAULT_TIPO_CONTENIDO)
            .activo(DEFAULT_ACTIVO)
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
        contenido.setNovedad(novedad);
        return contenido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contenido createUpdatedEntity(EntityManager em) {
        Contenido contenido = new Contenido()
            .descripcion(UPDATED_DESCRIPCION)
            .videoUrl(UPDATED_VIDEO_URL)
            .imagenURl(UPDATED_IMAGEN_U_RL)
            .imagenURlContentType(UPDATED_IMAGEN_U_RL_CONTENT_TYPE)
            .audio(UPDATED_AUDIO)
            .tipoContenido(UPDATED_TIPO_CONTENIDO)
            .activo(UPDATED_ACTIVO)
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
        contenido.setNovedad(novedad);
        return contenido;
    }

    @BeforeEach
    public void initTest() {
        contenido = createEntity(em);
    }

    @Test
    @Transactional
    public void createContenido() throws Exception {
        int databaseSizeBeforeCreate = contenidoRepository.findAll().size();

        // Create the Contenido
        restContenidoMockMvc.perform(post("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenido)))
            .andExpect(status().isCreated());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeCreate + 1);
        Contenido testContenido = contenidoList.get(contenidoList.size() - 1);
        assertThat(testContenido.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testContenido.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testContenido.getImagenURl()).isEqualTo(DEFAULT_IMAGEN_U_RL);
        assertThat(testContenido.getImagenURlContentType()).isEqualTo(DEFAULT_IMAGEN_U_RL_CONTENT_TYPE);
        assertThat(testContenido.getAudio()).isEqualTo(DEFAULT_AUDIO);
        assertThat(testContenido.getTipoContenido()).isEqualTo(DEFAULT_TIPO_CONTENIDO);
        assertThat(testContenido.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testContenido.getFechacreacion()).isEqualTo(DEFAULT_FECHACREACION);
    }

    @Test
    @Transactional
    public void createContenidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contenidoRepository.findAll().size();

        // Create the Contenido with an existing ID
        contenido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContenidoMockMvc.perform(post("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenido)))
            .andExpect(status().isBadRequest());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContenidos() throws Exception {
        // Initialize the database
        contenidoRepository.saveAndFlush(contenido);

        // Get all the contenidoList
        restContenidoMockMvc.perform(get("/api/contenidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contenido.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].imagenURlContentType").value(hasItem(DEFAULT_IMAGEN_U_RL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenURl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_U_RL))))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(DEFAULT_AUDIO)))
            .andExpect(jsonPath("$.[*].tipoContenido").value(hasItem(DEFAULT_TIPO_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].fechacreacion").value(hasItem(DEFAULT_FECHACREACION.toString())));
    }
    
    @Test
    @Transactional
    public void getContenido() throws Exception {
        // Initialize the database
        contenidoRepository.saveAndFlush(contenido);

        // Get the contenido
        restContenidoMockMvc.perform(get("/api/contenidos/{id}", contenido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contenido.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL))
            .andExpect(jsonPath("$.imagenURlContentType").value(DEFAULT_IMAGEN_U_RL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenURl").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_U_RL)))
            .andExpect(jsonPath("$.audio").value(DEFAULT_AUDIO))
            .andExpect(jsonPath("$.tipoContenido").value(DEFAULT_TIPO_CONTENIDO.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.fechacreacion").value(DEFAULT_FECHACREACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContenido() throws Exception {
        // Get the contenido
        restContenidoMockMvc.perform(get("/api/contenidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContenido() throws Exception {
        // Initialize the database
        contenidoRepository.saveAndFlush(contenido);

        int databaseSizeBeforeUpdate = contenidoRepository.findAll().size();

        // Update the contenido
        Contenido updatedContenido = contenidoRepository.findById(contenido.getId()).get();
        // Disconnect from session so that the updates on updatedContenido are not directly saved in db
        em.detach(updatedContenido);
        updatedContenido
            .descripcion(UPDATED_DESCRIPCION)
            .videoUrl(UPDATED_VIDEO_URL)
            .imagenURl(UPDATED_IMAGEN_U_RL)
            .imagenURlContentType(UPDATED_IMAGEN_U_RL_CONTENT_TYPE)
            .audio(UPDATED_AUDIO)
            .tipoContenido(UPDATED_TIPO_CONTENIDO)
            .activo(UPDATED_ACTIVO)
            .fechacreacion(UPDATED_FECHACREACION);

        restContenidoMockMvc.perform(put("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContenido)))
            .andExpect(status().isOk());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeUpdate);
        Contenido testContenido = contenidoList.get(contenidoList.size() - 1);
        assertThat(testContenido.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testContenido.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testContenido.getImagenURl()).isEqualTo(UPDATED_IMAGEN_U_RL);
        assertThat(testContenido.getImagenURlContentType()).isEqualTo(UPDATED_IMAGEN_U_RL_CONTENT_TYPE);
        assertThat(testContenido.getAudio()).isEqualTo(UPDATED_AUDIO);
        assertThat(testContenido.getTipoContenido()).isEqualTo(UPDATED_TIPO_CONTENIDO);
        assertThat(testContenido.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testContenido.getFechacreacion()).isEqualTo(UPDATED_FECHACREACION);
    }

    @Test
    @Transactional
    public void updateNonExistingContenido() throws Exception {
        int databaseSizeBeforeUpdate = contenidoRepository.findAll().size();

        // Create the Contenido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContenidoMockMvc.perform(put("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenido)))
            .andExpect(status().isBadRequest());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContenido() throws Exception {
        // Initialize the database
        contenidoRepository.saveAndFlush(contenido);

        int databaseSizeBeforeDelete = contenidoRepository.findAll().size();

        // Delete the contenido
        restContenidoMockMvc.perform(delete("/api/contenidos/{id}", contenido.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
