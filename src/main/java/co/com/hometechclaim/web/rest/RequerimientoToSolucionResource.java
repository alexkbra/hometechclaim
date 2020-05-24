package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.RequerimientoToSolucion;
import co.com.hometechclaim.repository.RequerimientoToSolucionRepository;
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
 * REST controller for managing {@link co.com.hometechclaim.domain.RequerimientoToSolucion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RequerimientoToSolucionResource {

    private final Logger log = LoggerFactory.getLogger(RequerimientoToSolucionResource.class);

    private static final String ENTITY_NAME = "requerimientoToSolucion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequerimientoToSolucionRepository requerimientoToSolucionRepository;

    public RequerimientoToSolucionResource(RequerimientoToSolucionRepository requerimientoToSolucionRepository) {
        this.requerimientoToSolucionRepository = requerimientoToSolucionRepository;
    }

    /**
     * {@code POST  /requerimiento-to-solucions} : Create a new requerimientoToSolucion.
     *
     * @param requerimientoToSolucion the requerimientoToSolucion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requerimientoToSolucion, or with status {@code 400 (Bad Request)} if the requerimientoToSolucion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requerimiento-to-solucions")
    public ResponseEntity<RequerimientoToSolucion> createRequerimientoToSolucion(@Valid @RequestBody RequerimientoToSolucion requerimientoToSolucion) throws URISyntaxException {
        log.debug("REST request to save RequerimientoToSolucion : {}", requerimientoToSolucion);
        if (requerimientoToSolucion.getId() != null) {
            throw new BadRequestAlertException("A new requerimientoToSolucion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequerimientoToSolucion result = requerimientoToSolucionRepository.save(requerimientoToSolucion);
        return ResponseEntity.created(new URI("/api/requerimiento-to-solucions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requerimiento-to-solucions} : Updates an existing requerimientoToSolucion.
     *
     * @param requerimientoToSolucion the requerimientoToSolucion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requerimientoToSolucion,
     * or with status {@code 400 (Bad Request)} if the requerimientoToSolucion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requerimientoToSolucion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requerimiento-to-solucions")
    public ResponseEntity<RequerimientoToSolucion> updateRequerimientoToSolucion(@Valid @RequestBody RequerimientoToSolucion requerimientoToSolucion) throws URISyntaxException {
        log.debug("REST request to update RequerimientoToSolucion : {}", requerimientoToSolucion);
        if (requerimientoToSolucion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequerimientoToSolucion result = requerimientoToSolucionRepository.save(requerimientoToSolucion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requerimientoToSolucion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /requerimiento-to-solucions} : get all the requerimientoToSolucions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requerimientoToSolucions in body.
     */
    @GetMapping("/requerimiento-to-solucions")
    public ResponseEntity<List<RequerimientoToSolucion>> getAllRequerimientoToSolucions(Pageable pageable) {
        log.debug("REST request to get a page of RequerimientoToSolucions");
        Page<RequerimientoToSolucion> page = requerimientoToSolucionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /requerimiento-to-solucions/:id} : get the "id" requerimientoToSolucion.
     *
     * @param id the id of the requerimientoToSolucion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requerimientoToSolucion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requerimiento-to-solucions/{id}")
    public ResponseEntity<RequerimientoToSolucion> getRequerimientoToSolucion(@PathVariable Long id) {
        log.debug("REST request to get RequerimientoToSolucion : {}", id);
        Optional<RequerimientoToSolucion> requerimientoToSolucion = requerimientoToSolucionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requerimientoToSolucion);
    }

    /**
     * {@code DELETE  /requerimiento-to-solucions/:id} : delete the "id" requerimientoToSolucion.
     *
     * @param id the id of the requerimientoToSolucion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requerimiento-to-solucions/{id}")
    public ResponseEntity<Void> deleteRequerimientoToSolucion(@PathVariable Long id) {
        log.debug("REST request to delete RequerimientoToSolucion : {}", id);
        requerimientoToSolucionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
