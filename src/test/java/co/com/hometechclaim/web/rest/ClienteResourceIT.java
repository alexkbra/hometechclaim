package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.HometechclaimApp;
import co.com.hometechclaim.domain.Cliente;
import co.com.hometechclaim.domain.Dealer;
import co.com.hometechclaim.repository.ClienteRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static co.com.hometechclaim.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@SpringBootTest(classes = HometechclaimApp.class)
public class ClienteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_DEALER = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_DEALER = "BBBBBBBBBB";

    private static final Long DEFAULT_IDCIUDAD = 1L;
    private static final Long UPDATED_IDCIUDAD = 2L;

    private static final String DEFAULT_TELEFONOCELULAR = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOCELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONOFIJO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOFIJO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONOEMPRESARIAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONOEMPRESARIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCIONRESIDENCIAL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCIONRESIDENCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCIONEMPRESARIAL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCIONEMPRESARIAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHANACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHANACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IDUSUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDUSUARIO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    @Autowired
    private ClienteRepository clienteRepository;

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

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteResource clienteResource = new ClienteResource(clienteRepository);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
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
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .correo(DEFAULT_CORREO)
            .codigoDealer(DEFAULT_CODIGO_DEALER)
            .idciudad(DEFAULT_IDCIUDAD)
            .telefonocelular(DEFAULT_TELEFONOCELULAR)
            .telefonofijo(DEFAULT_TELEFONOFIJO)
            .telefonoempresarial(DEFAULT_TELEFONOEMPRESARIAL)
            .direccionresidencial(DEFAULT_DIRECCIONRESIDENCIAL)
            .direccionempresarial(DEFAULT_DIRECCIONEMPRESARIAL)
            .fechanacimiento(DEFAULT_FECHANACIMIENTO)
            .idusuario(DEFAULT_IDUSUARIO)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
        // Add required entity
        Dealer dealer;
        if (TestUtil.findAll(em, Dealer.class).isEmpty()) {
            dealer = DealerResourceIT.createEntity(em);
            em.persist(dealer);
            em.flush();
        } else {
            dealer = TestUtil.findAll(em, Dealer.class).get(0);
        }
        cliente.setDealer(dealer);
        return cliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .correo(UPDATED_CORREO)
            .codigoDealer(UPDATED_CODIGO_DEALER)
            .idciudad(UPDATED_IDCIUDAD)
            .telefonocelular(UPDATED_TELEFONOCELULAR)
            .telefonofijo(UPDATED_TELEFONOFIJO)
            .telefonoempresarial(UPDATED_TELEFONOEMPRESARIAL)
            .direccionresidencial(UPDATED_DIRECCIONRESIDENCIAL)
            .direccionempresarial(UPDATED_DIRECCIONEMPRESARIAL)
            .fechanacimiento(UPDATED_FECHANACIMIENTO)
            .idusuario(UPDATED_IDUSUARIO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
        // Add required entity
        Dealer dealer;
        if (TestUtil.findAll(em, Dealer.class).isEmpty()) {
            dealer = DealerResourceIT.createUpdatedEntity(em);
            em.persist(dealer);
            em.flush();
        } else {
            dealer = TestUtil.findAll(em, Dealer.class).get(0);
        }
        cliente.setDealer(dealer);
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCliente.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testCliente.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testCliente.getCodigoDealer()).isEqualTo(DEFAULT_CODIGO_DEALER);
        assertThat(testCliente.getIdciudad()).isEqualTo(DEFAULT_IDCIUDAD);
        assertThat(testCliente.getTelefonocelular()).isEqualTo(DEFAULT_TELEFONOCELULAR);
        assertThat(testCliente.getTelefonofijo()).isEqualTo(DEFAULT_TELEFONOFIJO);
        assertThat(testCliente.getTelefonoempresarial()).isEqualTo(DEFAULT_TELEFONOEMPRESARIAL);
        assertThat(testCliente.getDireccionresidencial()).isEqualTo(DEFAULT_DIRECCIONRESIDENCIAL);
        assertThat(testCliente.getDireccionempresarial()).isEqualTo(DEFAULT_DIRECCIONEMPRESARIAL);
        assertThat(testCliente.getFechanacimiento()).isEqualTo(DEFAULT_FECHANACIMIENTO);
        assertThat(testCliente.getIdusuario()).isEqualTo(DEFAULT_IDUSUARIO);
        assertThat(testCliente.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testCliente.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNombre(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setApellido(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechanacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setFechanacimiento(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].codigoDealer").value(hasItem(DEFAULT_CODIGO_DEALER)))
            .andExpect(jsonPath("$.[*].idciudad").value(hasItem(DEFAULT_IDCIUDAD.intValue())))
            .andExpect(jsonPath("$.[*].telefonocelular").value(hasItem(DEFAULT_TELEFONOCELULAR)))
            .andExpect(jsonPath("$.[*].telefonofijo").value(hasItem(DEFAULT_TELEFONOFIJO)))
            .andExpect(jsonPath("$.[*].telefonoempresarial").value(hasItem(DEFAULT_TELEFONOEMPRESARIAL)))
            .andExpect(jsonPath("$.[*].direccionresidencial").value(hasItem(DEFAULT_DIRECCIONRESIDENCIAL)))
            .andExpect(jsonPath("$.[*].direccionempresarial").value(hasItem(DEFAULT_DIRECCIONEMPRESARIAL)))
            .andExpect(jsonPath("$.[*].fechanacimiento").value(hasItem(DEFAULT_FECHANACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].idusuario").value(hasItem(DEFAULT_IDUSUARIO)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
    }
    
    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.codigoDealer").value(DEFAULT_CODIGO_DEALER))
            .andExpect(jsonPath("$.idciudad").value(DEFAULT_IDCIUDAD.intValue()))
            .andExpect(jsonPath("$.telefonocelular").value(DEFAULT_TELEFONOCELULAR))
            .andExpect(jsonPath("$.telefonofijo").value(DEFAULT_TELEFONOFIJO))
            .andExpect(jsonPath("$.telefonoempresarial").value(DEFAULT_TELEFONOEMPRESARIAL))
            .andExpect(jsonPath("$.direccionresidencial").value(DEFAULT_DIRECCIONRESIDENCIAL))
            .andExpect(jsonPath("$.direccionempresarial").value(DEFAULT_DIRECCIONEMPRESARIAL))
            .andExpect(jsonPath("$.fechanacimiento").value(DEFAULT_FECHANACIMIENTO.toString()))
            .andExpect(jsonPath("$.idusuario").value(DEFAULT_IDUSUARIO))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)));
    }

    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .correo(UPDATED_CORREO)
            .codigoDealer(UPDATED_CODIGO_DEALER)
            .idciudad(UPDATED_IDCIUDAD)
            .telefonocelular(UPDATED_TELEFONOCELULAR)
            .telefonofijo(UPDATED_TELEFONOFIJO)
            .telefonoempresarial(UPDATED_TELEFONOEMPRESARIAL)
            .direccionresidencial(UPDATED_DIRECCIONRESIDENCIAL)
            .direccionempresarial(UPDATED_DIRECCIONEMPRESARIAL)
            .fechanacimiento(UPDATED_FECHANACIMIENTO)
            .idusuario(UPDATED_IDUSUARIO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCliente)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCliente.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testCliente.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testCliente.getCodigoDealer()).isEqualTo(UPDATED_CODIGO_DEALER);
        assertThat(testCliente.getIdciudad()).isEqualTo(UPDATED_IDCIUDAD);
        assertThat(testCliente.getTelefonocelular()).isEqualTo(UPDATED_TELEFONOCELULAR);
        assertThat(testCliente.getTelefonofijo()).isEqualTo(UPDATED_TELEFONOFIJO);
        assertThat(testCliente.getTelefonoempresarial()).isEqualTo(UPDATED_TELEFONOEMPRESARIAL);
        assertThat(testCliente.getDireccionresidencial()).isEqualTo(UPDATED_DIRECCIONRESIDENCIAL);
        assertThat(testCliente.getDireccionempresarial()).isEqualTo(UPDATED_DIRECCIONEMPRESARIAL);
        assertThat(testCliente.getFechanacimiento()).isEqualTo(UPDATED_FECHANACIMIENTO);
        assertThat(testCliente.getIdusuario()).isEqualTo(UPDATED_IDUSUARIO);
        assertThat(testCliente.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testCliente.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Create the Cliente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
