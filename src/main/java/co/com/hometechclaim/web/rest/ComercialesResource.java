package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.Comerciales;
import co.com.hometechclaim.repository.ComercialesRepository;
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
 * REST controller for managing {@link co.com.hometechclaim.domain.Comerciales}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ComercialesResource {

    private final Logger log = LoggerFactory.getLogger(ComercialesResource.class);

    private static final String ENTITY_NAME = "comerciales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComercialesRepository comercialesRepository;

    public ComercialesResource(ComercialesRepository comercialesRepository) {
        this.comercialesRepository = comercialesRepository;
    }

    /**
     * {@code POST  /comerciales} : Create a new comerciales.
     *
     * @param comerciales the comerciales to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comerciales, or with status {@code 400 (Bad Request)} if the comerciales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comerciales")
    public ResponseEntity<Comerciales> createComerciales(@Valid @RequestBody Comerciales comerciales) throws URISyntaxException {
        log.debug("REST request to save Comerciales : {}", comerciales);
        if (comerciales.getId() != null) {
            throw new BadRequestAlertException("A new comerciales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comerciales result = comercialesRepository.save(comerciales);
        return ResponseEntity.created(new URI("/api/comerciales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comerciales} : Updates an existing comerciales.
     *
     * @param comerciales the comerciales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comerciales,
     * or with status {@code 400 (Bad Request)} if the comerciales is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comerciales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comerciales")
    public ResponseEntity<Comerciales> updateComerciales(@Valid @RequestBody Comerciales comerciales) throws URISyntaxException {
        log.debug("REST request to update Comerciales : {}", comerciales);
        if (comerciales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Comerciales result = comercialesRepository.save(comerciales);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comerciales.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comerciales} : get all the comerciales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comerciales in body.
     */
    @GetMapping("/comerciales")
    public ResponseEntity<List<Comerciales>> getAllComerciales(Pageable pageable) {
        log.debug("REST request to get a page of Comerciales");
        Page<Comerciales> page = comercialesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comerciales/:id} : get the "id" comerciales.
     *
     * @param id the id of the comerciales to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comerciales, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comerciales/{id}")
    public ResponseEntity<Comerciales> getComerciales(@PathVariable Long id) {
        log.debug("REST request to get Comerciales : {}", id);
        Optional<Comerciales> comerciales = comercialesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(comerciales);
    }

    /**
     * {@code DELETE  /comerciales/:id} : delete the "id" comerciales.
     *
     * @param id the id of the comerciales to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comerciales/{id}")
    public ResponseEntity<Void> deleteComerciales(@PathVariable Long id) {
        log.debug("REST request to delete Comerciales : {}", id);
        comercialesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
