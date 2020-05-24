package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Novedad;
import co.com.hometechclaim.repository.NovedadRepository;
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
import java.util.List;

import static co.com.hometechclaim.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NovedadResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class NovedadResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_SUBTITULO = "AAAAAAAAAA";
    private static final String UPDATED_SUBTITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    @Autowired
    private NovedadRepository novedadRepository;

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

    private MockMvc restNovedadMockMvc;

    private Novedad novedad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NovedadResource novedadResource = new NovedadResource(novedadRepository);
        this.restNovedadMockMvc = MockMvcBuilders.standaloneSetup(novedadResource)
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
    public static Novedad createEntity(EntityManager em) {
        Novedad novedad = new Novedad()
            .titulo(DEFAULT_TITULO)
            .subtitulo(DEFAULT_SUBTITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
        return novedad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Novedad createUpdatedEntity(EntityManager em) {
        Novedad novedad = new Novedad()
            .titulo(UPDATED_TITULO)
            .subtitulo(UPDATED_SUBTITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
        return novedad;
    }

    @BeforeEach
    public void initTest() {
        novedad = createEntity(em);
    }

    @Test
    @Transactional
    public void createNovedad() throws Exception {
        int databaseSizeBeforeCreate = novedadRepository.findAll().size();

        // Create the Novedad
        restNovedadMockMvc.perform(post("/api/novedads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(novedad)))
            .andExpect(status().isCreated());

        // Validate the Novedad in the database
        List<Novedad> novedadList = novedadRepository.findAll();
        assertThat(novedadList).hasSize(databaseSizeBeforeCreate + 1);
        Novedad testNovedad = novedadList.get(novedadList.size() - 1);
        assertThat(testNovedad.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testNovedad.getSubtitulo()).isEqualTo(DEFAULT_SUBTITULO);
        assertThat(testNovedad.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testNovedad.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testNovedad.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createNovedadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = novedadRepository.findAll().size();

        // Create the Novedad with an existing ID
        novedad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNovedadMockMvc.perform(post("/api/novedads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(novedad)))
            .andExpect(status().isBadRequest());

        // Validate the Novedad in the database
        List<Novedad> novedadList = novedadRepository.findAll();
        assertThat(novedadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = novedadRepository.findAll().size();
        // set the field null
        novedad.setTitulo(null);

        // Create the Novedad, which fails.

        restNovedadMockMvc.perform(post("/api/novedads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(novedad)))
            .andExpect(status().isBadRequest());

        List<Novedad> novedadList = novedadRepository.findAll();
        assertThat(novedadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubtituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = novedadRepository.findAll().size();
        // set the field null
        novedad.setSubtitulo(null);

        // Create the Novedad, which fails.

        restNovedadMockMvc.perform(post("/api/novedads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(novedad)))
            .andExpect(status().isBadRequest());

        List<Novedad> novedadList = novedadRepository.findAll();
        assertThat(novedadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = novedadRepository.findAll().size();
        // set the field null
        novedad.setDescripcion(null);

        // Create the Novedad, which fails.

        restNovedadMockMvc.perform(post("/api/novedads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(novedad)))
            .andExpect(status().isBadRequest());

        List<Novedad> novedadList = novedadRepository.findAll();
        assertThat(novedadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNovedads() throws Exception {
        // Initialize the database
        novedadRepository.saveAndFlush(novedad);

        // Get all the novedadList
        restNovedadMockMvc.perform(get("/api/novedads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(novedad.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].subtitulo").value(hasItem(DEFAULT_SUBTITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
    }
    
    @Test
    @Transactional
    public void getNovedad() throws Exception {
        // Initialize the database
        novedadRepository.saveAndFlush(novedad);

        // Get the novedad
        restNovedadMockMvc.perform(get("/api/novedads/{id}", novedad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(novedad.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.subtitulo").value(DEFAULT_SUBTITULO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)));
    }

    @Test
    @Transactional
    public void getNonExistingNovedad() throws Exception {
        // Get the novedad
        restNovedadMockMvc.perform(get("/api/novedads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNovedad() throws Exception {
        // Initialize the database
        novedadRepository.saveAndFlush(novedad);

        int databaseSizeBeforeUpdate = novedadRepository.findAll().size();

        // Update the novedad
        Novedad updatedNovedad = novedadRepository.findById(novedad.getId()).get();
        // Disconnect from session so that the updates on updatedNovedad are not directly saved in db
        em.detach(updatedNovedad);
        updatedNovedad
            .titulo(UPDATED_TITULO)
            .subtitulo(UPDATED_SUBTITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);

        restNovedadMockMvc.perform(put("/api/novedads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNovedad)))
            .andExpect(status().isOk());

        // Validate the Novedad in the database
        List<Novedad> novedadList = novedadRepository.findAll();
        assertThat(novedadList).hasSize(databaseSizeBeforeUpdate);
        Novedad testNovedad = novedadList.get(novedadList.size() - 1);
        assertThat(testNovedad.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testNovedad.getSubtitulo()).isEqualTo(UPDATED_SUBTITULO);
        assertThat(testNovedad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNovedad.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testNovedad.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingNovedad() throws Exception {
        int databaseSizeBeforeUpdate = novedadRepository.findAll().size();

        // Create the Novedad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNovedadMockMvc.perform(put("/api/novedads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(novedad)))
            .andExpect(status().isBadRequest());

        // Validate the Novedad in the database
        List<Novedad> novedadList = novedadRepository.findAll();
        assertThat(novedadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNovedad() throws Exception {
        // Initialize the database
        novedadRepository.saveAndFlush(novedad);

        int databaseSizeBeforeDelete = novedadRepository.findAll().size();

        // Delete the novedad
        restNovedadMockMvc.perform(delete("/api/novedads/{id}", novedad.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Novedad> novedadList = novedadRepository.findAll();
        assertThat(novedadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
