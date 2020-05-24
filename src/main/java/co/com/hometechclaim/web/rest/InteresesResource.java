package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.Intereses;
import co.com.hometechclaim.repository.InteresesRepository;
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
 * REST controller for managing {@link co.com.hometechclaim.domain.Intereses}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InteresesResource {

    private final Logger log = LoggerFactory.getLogger(InteresesResource.class);

    private static final String ENTITY_NAME = "intereses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InteresesRepository interesesRepository;

    public InteresesResource(InteresesRepository interesesRepository) {
        this.interesesRepository = interesesRepository;
    }

    /**
     * {@code POST  /intereses} : Create a new intereses.
     *
     * @param intereses the intereses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intereses, or with status {@code 400 (Bad Request)} if the intereses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/intereses")
    public ResponseEntity<Intereses> createIntereses(@Valid @RequestBody Intereses intereses) throws URISyntaxException {
        log.debug("REST request to save Intereses : {}", intereses);
        if (intereses.getId() != null) {
            throw new BadRequestAlertException("A new intereses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Intereses result = interesesRepository.save(intereses);
        return ResponseEntity.created(new URI("/api/intereses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /intereses} : Updates an existing intereses.
     *
     * @param intereses the intereses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intereses,
     * or with status {@code 400 (Bad Request)} if the intereses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intereses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/intereses")
    public ResponseEntity<Intereses> updateIntereses(@Valid @RequestBody Intereses intereses) throws URISyntaxException {
        log.debug("REST request to update Intereses : {}", intereses);
        if (intereses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Intereses result = interesesRepository.save(intereses);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intereses.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /intereses} : get all the intereses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intereses in body.
     */
    @GetMapping("/intereses")
    public ResponseEntity<List<Intereses>> getAllIntereses(Pageable pageable) {
        log.debug("REST request to get a page of Intereses");
        Page<Intereses> page = interesesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /intereses/:id} : get the "id" intereses.
     *
     * @param id the id of the intereses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intereses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/intereses/{id}")
    public ResponseEntity<Intereses> getIntereses(@PathVariable Long id) {
        log.debug("REST request to get Intereses : {}", id);
        Optional<Intereses> intereses = interesesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(intereses);
    }

    /**
     * {@code DELETE  /intereses/:id} : delete the "id" intereses.
     *
     * @param id the id of the intereses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/intereses/{id}")
    public ResponseEntity<Void> deleteIntereses(@PathVariable Long id) {
        log.debug("REST request to delete Intereses : {}", id);
        interesesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
