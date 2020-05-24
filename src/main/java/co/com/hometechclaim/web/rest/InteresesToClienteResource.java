package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.InteresesToCliente;
import co.com.hometechclaim.repository.InteresesToClienteRepository;
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
 * REST controller for managing {@link co.com.hometechclaim.domain.InteresesToCliente}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InteresesToClienteResource {

    private final Logger log = LoggerFactory.getLogger(InteresesToClienteResource.class);

    private static final String ENTITY_NAME = "interesesToCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InteresesToClienteRepository interesesToClienteRepository;

    public InteresesToClienteResource(InteresesToClienteRepository interesesToClienteRepository) {
        this.interesesToClienteRepository = interesesToClienteRepository;
    }

    /**
     * {@code POST  /intereses-to-clientes} : Create a new interesesToCliente.
     *
     * @param interesesToCliente the interesesToCliente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interesesToCliente, or with status {@code 400 (Bad Request)} if the interesesToCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/intereses-to-clientes")
    public ResponseEntity<InteresesToCliente> createInteresesToCliente(@Valid @RequestBody InteresesToCliente interesesToCliente) throws URISyntaxException {
        log.debug("REST request to save InteresesToCliente : {}", interesesToCliente);
        if (interesesToCliente.getId() != null) {
            throw new BadRequestAlertException("A new interesesToCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InteresesToCliente result = interesesToClienteRepository.save(interesesToCliente);
        return ResponseEntity.created(new URI("/api/intereses-to-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /intereses-to-clientes} : Updates an existing interesesToCliente.
     *
     * @param interesesToCliente the interesesToCliente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interesesToCliente,
     * or with status {@code 400 (Bad Request)} if the interesesToCliente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interesesToCliente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/intereses-to-clientes")
    public ResponseEntity<InteresesToCliente> updateInteresesToCliente(@Valid @RequestBody InteresesToCliente interesesToCliente) throws URISyntaxException {
        log.debug("REST request to update InteresesToCliente : {}", interesesToCliente);
        if (interesesToCliente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InteresesToCliente result = interesesToClienteRepository.save(interesesToCliente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interesesToCliente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /intereses-to-clientes} : get all the interesesToClientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interesesToClientes in body.
     */
    @GetMapping("/intereses-to-clientes")
    public ResponseEntity<List<InteresesToCliente>> getAllInteresesToClientes(Pageable pageable) {
        log.debug("REST request to get a page of InteresesToClientes");
        Page<InteresesToCliente> page = interesesToClienteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /intereses-to-clientes/:id} : get the "id" interesesToCliente.
     *
     * @param id the id of the interesesToCliente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interesesToCliente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/intereses-to-clientes/{id}")
    public ResponseEntity<InteresesToCliente> getInteresesToCliente(@PathVariable Long id) {
        log.debug("REST request to get InteresesToCliente : {}", id);
        Optional<InteresesToCliente> interesesToCliente = interesesToClienteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(interesesToCliente);
    }

    /**
     * {@code DELETE  /intereses-to-clientes/:id} : delete the "id" interesesToCliente.
     *
     * @param id the id of the interesesToCliente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/intereses-to-clientes/{id}")
    public ResponseEntity<Void> deleteInteresesToCliente(@PathVariable Long id) {
        log.debug("REST request to delete InteresesToCliente : {}", id);
        interesesToClienteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
