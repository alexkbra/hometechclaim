package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Requerimiento;
import co.com.hometechclaim.repository.RequerimientoRepository;
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

import co.com.hometechclaim.domain.enumeration.EstadoRequerimiento;
import co.com.hometechclaim.domain.enumeration.TipoRequerimiento;
/**
 * Integration tests for the {@link RequerimientoResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class RequerimientoResourceIT {

    private static final String DEFAULT_DETALLEPROBLEMA = "AAAAAAAAAA";
    private static final String UPDATED_DETALLEPROBLEMA = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHACREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHACREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHAACTUALIZACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHAACTUALIZACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final String DEFAULT_IDUSUARIOATENDIO = "AAAAAAAAAA";
    private static final String UPDATED_IDUSUARIOATENDIO = "BBBBBBBBBB";

    private static final String DEFAULT_IDUSUARIOACTUALIZO = "AAAAAAAAAA";
    private static final String UPDATED_IDUSUARIOACTUALIZO = "BBBBBBBBBB";

    private static final String DEFAULT_ID_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_ID_USUARIO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final EstadoRequerimiento DEFAULT_ESTADO_REQUERIMIENTO = EstadoRequerimiento.INICIADO;
    private static final EstadoRequerimiento UPDATED_ESTADO_REQUERIMIENTO = EstadoRequerimiento.ENPROCESO;

    private static final TipoRequerimiento DEFAULT_TIPO_REQUERIMIENTO = TipoRequerimiento.NUEVOEQUIPO;
    private static final TipoRequerimiento UPDATED_TIPO_REQUERIMIENTO = TipoRequerimiento.PROBLEMASEQUIPO;

    @Autowired
    private RequerimientoRepository requerimientoRepository;

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

    private MockMvc restRequerimientoMockMvc;

    private Requerimiento requerimiento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequerimientoResource requerimientoResource = new RequerimientoResource(requerimientoRepository);
        this.restRequerimientoMockMvc = MockMvcBuilders.standaloneSetup(requerimientoResource)
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
    public static Requerimiento createEntity(EntityManager em) {
        Requerimiento requerimiento = new Requerimiento()
            .detalleproblema(DEFAULT_DETALLEPROBLEMA)
            .fechacreacion(DEFAULT_FECHACREACION)
            .fechaactualizacion(DEFAULT_FECHAACTUALIZACION)
            .observaciones(DEFAULT_OBSERVACIONES)
            .idusuarioatendio(DEFAULT_IDUSUARIOATENDIO)
            .idusuarioactualizo(DEFAULT_IDUSUARIOACTUALIZO)
            .idUsuario(DEFAULT_ID_USUARIO)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .estadoRequerimiento(DEFAULT_ESTADO_REQUERIMIENTO)
            .tipoRequerimiento(DEFAULT_TIPO_REQUERIMIENTO);
        return requerimiento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requerimiento createUpdatedEntity(EntityManager em) {
        Requerimiento requerimiento = new Requerimiento()
            .detalleproblema(UPDATED_DETALLEPROBLEMA)
            .fechacreacion(UPDATED_FECHACREACION)
            .fechaactualizacion(UPDATED_FECHAACTUALIZACION)
            .observaciones(UPDATED_OBSERVACIONES)
            .idusuarioatendio(UPDATED_IDUSUARIOATENDIO)
            .idusuarioactualizo(UPDATED_IDUSUARIOACTUALIZO)
            .idUsuario(UPDATED_ID_USUARIO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .estadoRequerimiento(UPDATED_ESTADO_REQUERIMIENTO)
            .tipoRequerimiento(UPDATED_TIPO_REQUERIMIENTO);
        return requerimiento;
    }

    @BeforeEach
    public void initTest() {
        requerimiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequerimiento() throws Exception {
        int databaseSizeBeforeCreate = requerimientoRepository.findAll().size();

        // Create the Requerimiento
        restRequerimientoMockMvc.perform(post("/api/requerimientos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requerimiento)))
            .andExpect(status().isCreated());

        // Validate the Requerimiento in the database
        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeCreate + 1);
        Requerimiento testRequerimiento = requerimientoList.get(requerimientoList.size() - 1);
        assertThat(testRequerimiento.getDetalleproblema()).isEqualTo(DEFAULT_DETALLEPROBLEMA);
        assertThat(testRequerimiento.getFechacreacion()).isEqualTo(DEFAULT_FECHACREACION);
        assertThat(testRequerimiento.getFechaactualizacion()).isEqualTo(DEFAULT_FECHAACTUALIZACION);
        assertThat(testRequerimiento.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testRequerimiento.getIdusuarioatendio()).isEqualTo(DEFAULT_IDUSUARIOATENDIO);
        assertThat(testRequerimiento.getIdusuarioactualizo()).isEqualTo(DEFAULT_IDUSUARIOACTUALIZO);
        assertThat(testRequerimiento.getIdUsuario()).isEqualTo(DEFAULT_ID_USUARIO);
        assertThat(testRequerimiento.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testRequerimiento.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testRequerimiento.getEstadoRequerimiento()).isEqualTo(DEFAULT_ESTADO_REQUERIMIENTO);
        assertThat(testRequerimiento.getTipoRequerimiento()).isEqualTo(DEFAULT_TIPO_REQUERIMIENTO);
    }

    @Test
    @Transactional
    public void createRequerimientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requerimientoRepository.findAll().size();

        // Create the Requerimiento with an existing ID
        requerimiento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequerimientoMockMvc.perform(post("/api/requerimientos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requerimiento)))
            .andExpect(status().isBadRequest());

        // Validate the Requerimiento in the database
        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechacreacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = requerimientoRepository.findAll().size();
        // set the field null
        requerimiento.setFechacreacion(null);

        // Create the Requerimiento, which fails.

        restRequerimientoMockMvc.perform(post("/api/requerimientos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requerimiento)))
            .andExpect(status().isBadRequest());

        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequerimientos() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get all the requerimientoList
        restRequerimientoMockMvc.perform(get("/api/requerimientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requerimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].detalleproblema").value(hasItem(DEFAULT_DETALLEPROBLEMA.toString())))
            .andExpect(jsonPath("$.[*].fechacreacion").value(hasItem(DEFAULT_FECHACREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaactualizacion").value(hasItem(DEFAULT_FECHAACTUALIZACION.toString())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())))
            .andExpect(jsonPath("$.[*].idusuarioatendio").value(hasItem(DEFAULT_IDUSUARIOATENDIO)))
            .andExpect(jsonPath("$.[*].idusuarioactualizo").value(hasItem(DEFAULT_IDUSUARIOACTUALIZO)))
            .andExpect(jsonPath("$.[*].idUsuario").value(hasItem(DEFAULT_ID_USUARIO)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].estadoRequerimiento").value(hasItem(DEFAULT_ESTADO_REQUERIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoRequerimiento").value(hasItem(DEFAULT_TIPO_REQUERIMIENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getRequerimiento() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        // Get the requerimiento
        restRequerimientoMockMvc.perform(get("/api/requerimientos/{id}", requerimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requerimiento.getId().intValue()))
            .andExpect(jsonPath("$.detalleproblema").value(DEFAULT_DETALLEPROBLEMA.toString()))
            .andExpect(jsonPath("$.fechacreacion").value(DEFAULT_FECHACREACION.toString()))
            .andExpect(jsonPath("$.fechaactualizacion").value(DEFAULT_FECHAACTUALIZACION.toString()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()))
            .andExpect(jsonPath("$.idusuarioatendio").value(DEFAULT_IDUSUARIOATENDIO))
            .andExpect(jsonPath("$.idusuarioactualizo").value(DEFAULT_IDUSUARIOACTUALIZO))
            .andExpect(jsonPath("$.idUsuario").value(DEFAULT_ID_USUARIO))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.estadoRequerimiento").value(DEFAULT_ESTADO_REQUERIMIENTO.toString()))
            .andExpect(jsonPath("$.tipoRequerimiento").value(DEFAULT_TIPO_REQUERIMIENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequerimiento() throws Exception {
        // Get the requerimiento
        restRequerimientoMockMvc.perform(get("/api/requerimientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequerimiento() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        int databaseSizeBeforeUpdate = requerimientoRepository.findAll().size();

        // Update the requerimiento
        Requerimiento updatedRequerimiento = requerimientoRepository.findById(requerimiento.getId()).get();
        // Disconnect from session so that the updates on updatedRequerimiento are not directly saved in db
        em.detach(updatedRequerimiento);
        updatedRequerimiento
            .detalleproblema(UPDATED_DETALLEPROBLEMA)
            .fechacreacion(UPDATED_FECHACREACION)
            .fechaactualizacion(UPDATED_FECHAACTUALIZACION)
            .observaciones(UPDATED_OBSERVACIONES)
            .idusuarioatendio(UPDATED_IDUSUARIOATENDIO)
            .idusuarioactualizo(UPDATED_IDUSUARIOACTUALIZO)
            .idUsuario(UPDATED_ID_USUARIO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .estadoRequerimiento(UPDATED_ESTADO_REQUERIMIENTO)
            .tipoRequerimiento(UPDATED_TIPO_REQUERIMIENTO);

        restRequerimientoMockMvc.perform(put("/api/requerimientos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequerimiento)))
            .andExpect(status().isOk());

        // Validate the Requerimiento in the database
        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeUpdate);
        Requerimiento testRequerimiento = requerimientoList.get(requerimientoList.size() - 1);
        assertThat(testRequerimiento.getDetalleproblema()).isEqualTo(UPDATED_DETALLEPROBLEMA);
        assertThat(testRequerimiento.getFechacreacion()).isEqualTo(UPDATED_FECHACREACION);
        assertThat(testRequerimiento.getFechaactualizacion()).isEqualTo(UPDATED_FECHAACTUALIZACION);
        assertThat(testRequerimiento.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testRequerimiento.getIdusuarioatendio()).isEqualTo(UPDATED_IDUSUARIOATENDIO);
        assertThat(testRequerimiento.getIdusuarioactualizo()).isEqualTo(UPDATED_IDUSUARIOACTUALIZO);
        assertThat(testRequerimiento.getIdUsuario()).isEqualTo(UPDATED_ID_USUARIO);
        assertThat(testRequerimiento.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testRequerimiento.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testRequerimiento.getEstadoRequerimiento()).isEqualTo(UPDATED_ESTADO_REQUERIMIENTO);
        assertThat(testRequerimiento.getTipoRequerimiento()).isEqualTo(UPDATED_TIPO_REQUERIMIENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingRequerimiento() throws Exception {
        int databaseSizeBeforeUpdate = requerimientoRepository.findAll().size();

        // Create the Requerimiento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequerimientoMockMvc.perform(put("/api/requerimientos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requerimiento)))
            .andExpect(status().isBadRequest());

        // Validate the Requerimiento in the database
        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRequerimiento() throws Exception {
        // Initialize the database
        requerimientoRepository.saveAndFlush(requerimiento);

        int databaseSizeBeforeDelete = requerimientoRepository.findAll().size();

        // Delete the requerimiento
        restRequerimientoMockMvc.perform(delete("/api/requerimientos/{id}", requerimiento.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Requerimiento> requerimientoList = requerimientoRepository.findAll();
        assertThat(requerimientoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
