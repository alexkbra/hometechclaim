package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.Novedad;
import co.com.hometechclaim.repository.NovedadRepository;
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
 * REST controller for managing {@link co.com.hometechclaim.domain.Novedad}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NovedadResource {

    private final Logger log = LoggerFactory.getLogger(NovedadResource.class);

    private static final String ENTITY_NAME = "novedad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NovedadRepository novedadRepository;

    public NovedadResource(NovedadRepository novedadRepository) {
        this.novedadRepository = novedadRepository;
    }

    /**
     * {@code POST  /novedads} : Create a new novedad.
     *
     * @param novedad the novedad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new novedad, or with status {@code 400 (Bad Request)} if the novedad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/novedads")
    public ResponseEntity<Novedad> createNovedad(@Valid @RequestBody Novedad novedad) throws URISyntaxException {
        log.debug("REST request to save Novedad : {}", novedad);
        if (novedad.getId() != null) {
            throw new BadRequestAlertException("A new novedad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Novedad result = novedadRepository.save(novedad);
        return ResponseEntity.created(new URI("/api/novedads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /novedads} : Updates an existing novedad.
     *
     * @param novedad the novedad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated novedad,
     * or with status {@code 400 (Bad Request)} if the novedad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the novedad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/novedads")
    public ResponseEntity<Novedad> updateNovedad(@Valid @RequestBody Novedad novedad) throws URISyntaxException {
        log.debug("REST request to update Novedad : {}", novedad);
        if (novedad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Novedad result = novedadRepository.save(novedad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, novedad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /novedads} : get all the novedads.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of novedads in body.
     */
    @GetMapping("/novedads")
    public ResponseEntity<List<Novedad>> getAllNovedads(Pageable pageable) {
        log.debug("REST request to get a page of Novedads");
        Page<Novedad> page = novedadRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /novedads/:id} : get the "id" novedad.
     *
     * @param id the id of the novedad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the novedad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/novedads/{id}")
    public ResponseEntity<Novedad> getNovedad(@PathVariable Long id) {
        log.debug("REST request to get Novedad : {}", id);
        Optional<Novedad> novedad = novedadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(novedad);
    }

    /**
     * {@code DELETE  /novedads/:id} : delete the "id" novedad.
     *
     * @param id the id of the novedad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/novedads/{id}")
    public ResponseEntity<Void> deleteNovedad(@PathVariable Long id) {
        log.debug("REST request to delete Novedad : {}", id);
        novedadRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
