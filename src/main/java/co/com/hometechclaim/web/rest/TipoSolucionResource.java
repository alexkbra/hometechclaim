package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.TipoSolucion;
import co.com.hometechclaim.repository.TipoSolucionRepository;
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
 * REST controller for managing {@link co.com.hometechclaim.domain.TipoSolucion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoSolucionResource {

    private final Logger log = LoggerFactory.getLogger(TipoSolucionResource.class);

    private static final String ENTITY_NAME = "tipoSolucion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoSolucionRepository tipoSolucionRepository;

    public TipoSolucionResource(TipoSolucionRepository tipoSolucionRepository) {
        this.tipoSolucionRepository = tipoSolucionRepository;
    }

    /**
     * {@code POST  /tipo-solucions} : Create a new tipoSolucion.
     *
     * @param tipoSolucion the tipoSolucion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoSolucion, or with status {@code 400 (Bad Request)} if the tipoSolucion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-solucions")
    public ResponseEntity<TipoSolucion> createTipoSolucion(@Valid @RequestBody TipoSolucion tipoSolucion) throws URISyntaxException {
        log.debug("REST request to save TipoSolucion : {}", tipoSolucion);
        if (tipoSolucion.getId() != null) {
            throw new BadRequestAlertException("A new tipoSolucion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoSolucion result = tipoSolucionRepository.save(tipoSolucion);
        return ResponseEntity.created(new URI("/api/tipo-solucions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-solucions} : Updates an existing tipoSolucion.
     *
     * @param tipoSolucion the tipoSolucion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoSolucion,
     * or with status {@code 400 (Bad Request)} if the tipoSolucion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoSolucion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-solucions")
    public ResponseEntity<TipoSolucion> updateTipoSolucion(@Valid @RequestBody TipoSolucion tipoSolucion) throws URISyntaxException {
        log.debug("REST request to update TipoSolucion : {}", tipoSolucion);
        if (tipoSolucion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoSolucion result = tipoSolucionRepository.save(tipoSolucion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoSolucion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-solucions} : get all the tipoSolucions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoSolucions in body.
     */
    @GetMapping("/tipo-solucions")
    public ResponseEntity<List<TipoSolucion>> getAllTipoSolucions(Pageable pageable) {
        log.debug("REST request to get a page of TipoSolucions");
        Page<TipoSolucion> page = tipoSolucionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-solucions/:id} : get the "id" tipoSolucion.
     *
     * @param id the id of the tipoSolucion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoSolucion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-solucions/{id}")
    public ResponseEntity<TipoSolucion> getTipoSolucion(@PathVariable Long id) {
        log.debug("REST request to get TipoSolucion : {}", id);
        Optional<TipoSolucion> tipoSolucion = tipoSolucionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoSolucion);
    }

    /**
     * {@code DELETE  /tipo-solucions/:id} : delete the "id" tipoSolucion.
     *
     * @param id the id of the tipoSolucion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-solucions/{id}")
    public ResponseEntity<Void> deleteTipoSolucion(@PathVariable Long id) {
        log.debug("REST request to delete TipoSolucion : {}", id);
        tipoSolucionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
