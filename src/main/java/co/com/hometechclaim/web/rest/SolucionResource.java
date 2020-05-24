package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.Solucion;
import co.com.hometechclaim.repository.SolucionRepository;
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
 * REST controller for managing {@link co.com.hometechclaim.domain.Solucion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SolucionResource {

    private final Logger log = LoggerFactory.getLogger(SolucionResource.class);

    private static final String ENTITY_NAME = "solucion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolucionRepository solucionRepository;

    public SolucionResource(SolucionRepository solucionRepository) {
        this.solucionRepository = solucionRepository;
    }

    /**
     * {@code POST  /solucions} : Create a new solucion.
     *
     * @param solucion the solucion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solucion, or with status {@code 400 (Bad Request)} if the solucion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solucions")
    public ResponseEntity<Solucion> createSolucion(@Valid @RequestBody Solucion solucion) throws URISyntaxException {
        log.debug("REST request to save Solucion : {}", solucion);
        if (solucion.getId() != null) {
            throw new BadRequestAlertException("A new solucion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Solucion result = solucionRepository.save(solucion);
        return ResponseEntity.created(new URI("/api/solucions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solucions} : Updates an existing solucion.
     *
     * @param solucion the solucion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solucion,
     * or with status {@code 400 (Bad Request)} if the solucion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solucion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solucions")
    public ResponseEntity<Solucion> updateSolucion(@Valid @RequestBody Solucion solucion) throws URISyntaxException {
        log.debug("REST request to update Solucion : {}", solucion);
        if (solucion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Solucion result = solucionRepository.save(solucion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, solucion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /solucions} : get all the solucions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solucions in body.
     */
    @GetMapping("/solucions")
    public ResponseEntity<List<Solucion>> getAllSolucions(Pageable pageable) {
        log.debug("REST request to get a page of Solucions");
        Page<Solucion> page = solucionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /solucions/:id} : get the "id" solucion.
     *
     * @param id the id of the solucion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solucion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solucions/{id}")
    public ResponseEntity<Solucion> getSolucion(@PathVariable Long id) {
        log.debug("REST request to get Solucion : {}", id);
        Optional<Solucion> solucion = solucionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(solucion);
    }

    /**
     * {@code DELETE  /solucions/:id} : delete the "id" solucion.
     *
     * @param id the id of the solucion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solucions/{id}")
    public ResponseEntity<Void> deleteSolucion(@PathVariable Long id) {
        log.debug("REST request to delete Solucion : {}", id);
        solucionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
