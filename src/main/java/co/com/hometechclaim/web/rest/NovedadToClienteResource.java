package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.NovedadToCliente;
import co.com.hometechclaim.repository.NovedadToClienteRepository;
import co.com.hometechclaim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.com.hometechclaim.domain.NovedadToCliente}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NovedadToClienteResource {

    private final Logger log = LoggerFactory.getLogger(NovedadToClienteResource.class);

    private static final String ENTITY_NAME = "novedadToCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NovedadToClienteRepository novedadToClienteRepository;

    public NovedadToClienteResource(NovedadToClienteRepository novedadToClienteRepository) {
        this.novedadToClienteRepository = novedadToClienteRepository;
    }

    /**
     * {@code POST  /novedad-to-clientes} : Create a new novedadToCliente.
     *
     * @param novedadToCliente the novedadToCliente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new novedadToCliente, or with status {@code 400 (Bad Request)} if the novedadToCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/novedad-to-clientes")
    public ResponseEntity<NovedadToCliente> createNovedadToCliente(@Valid @RequestBody NovedadToCliente novedadToCliente) throws URISyntaxException {
        log.debug("REST request to save NovedadToCliente : {}", novedadToCliente);
        if (novedadToCliente.getId() != null) {
            throw new BadRequestAlertException("A new novedadToCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NovedadToCliente result = novedadToClienteRepository.save(novedadToCliente);
        return ResponseEntity.created(new URI("/api/novedad-to-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /novedad-to-clientes} : Updates an existing novedadToCliente.
     *
     * @param novedadToCliente the novedadToCliente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated novedadToCliente,
     * or with status {@code 400 (Bad Request)} if the novedadToCliente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the novedadToCliente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/novedad-to-clientes")
    public ResponseEntity<NovedadToCliente> updateNovedadToCliente(@Valid @RequestBody NovedadToCliente novedadToCliente) throws URISyntaxException {
        log.debug("REST request to update NovedadToCliente : {}", novedadToCliente);
        if (novedadToCliente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NovedadToCliente result = novedadToClienteRepository.save(novedadToCliente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, novedadToCliente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /novedad-to-clientes} : get all the novedadToClientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of novedadToClientes in body.
     */
    @GetMapping("/novedad-to-clientes")
    public ResponseEntity<List<NovedadToCliente>> getAllNovedadToClientes(Pageable pageable) {
        log.debug("REST request to get a page of NovedadToClientes");
        Page<NovedadToCliente> page = novedadToClienteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /novedad-to-clientes/:id} : get the "id" novedadToCliente.
     *
     * @param id the id of the novedadToCliente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the novedadToCliente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/novedad-to-clientes/{id}")
    public ResponseEntity<NovedadToCliente> getNovedadToCliente(@PathVariable Long id) {
        log.debug("REST request to get NovedadToCliente : {}", id);
        Optional<NovedadToCliente> novedadToCliente = novedadToClienteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(novedadToCliente);
    }

    /**
     * {@code DELETE  /novedad-to-clientes/:id} : delete the "id" novedadToCliente.
     *
     * @param id the id of the novedadToCliente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/novedad-to-clientes/{id}")
    public ResponseEntity<Void> deleteNovedadToCliente(@PathVariable Long id) {
        log.debug("REST request to delete NovedadToCliente : {}", id);
        novedadToClienteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
