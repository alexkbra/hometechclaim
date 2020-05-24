package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.Cotizacion;
import co.com.hometechclaim.repository.CotizacionRepository;
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
 * REST controller for managing {@link co.com.hometechclaim.domain.Cotizacion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CotizacionResource {

    private final Logger log = LoggerFactory.getLogger(CotizacionResource.class);

    private static final String ENTITY_NAME = "cotizacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CotizacionRepository cotizacionRepository;

    public CotizacionResource(CotizacionRepository cotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
    }

    /**
     * {@code POST  /cotizacions} : Create a new cotizacion.
     *
     * @param cotizacion the cotizacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cotizacion, or with status {@code 400 (Bad Request)} if the cotizacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cotizacions")
    public ResponseEntity<Cotizacion> createCotizacion(@Valid @RequestBody Cotizacion cotizacion) throws URISyntaxException {
        log.debug("REST request to save Cotizacion : {}", cotizacion);
        if (cotizacion.getId() != null) {
            throw new BadRequestAlertException("A new cotizacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cotizacion result = cotizacionRepository.save(cotizacion);
        return ResponseEntity.created(new URI("/api/cotizacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cotizacions} : Updates an existing cotizacion.
     *
     * @param cotizacion the cotizacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cotizacion,
     * or with status {@code 400 (Bad Request)} if the cotizacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cotizacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cotizacions")
    public ResponseEntity<Cotizacion> updateCotizacion(@Valid @RequestBody Cotizacion cotizacion) throws URISyntaxException {
        log.debug("REST request to update Cotizacion : {}", cotizacion);
        if (cotizacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cotizacion result = cotizacionRepository.save(cotizacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cotizacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cotizacions} : get all the cotizacions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cotizacions in body.
     */
    @GetMapping("/cotizacions")
    public ResponseEntity<List<Cotizacion>> getAllCotizacions(Pageable pageable) {
        log.debug("REST request to get a page of Cotizacions");
        Page<Cotizacion> page = cotizacionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cotizacions/:id} : get the "id" cotizacion.
     *
     * @param id the id of the cotizacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cotizacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cotizacions/{id}")
    public ResponseEntity<Cotizacion> getCotizacion(@PathVariable Long id) {
        log.debug("REST request to get Cotizacion : {}", id);
        Optional<Cotizacion> cotizacion = cotizacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cotizacion);
    }

    /**
     * {@code DELETE  /cotizacions/:id} : delete the "id" cotizacion.
     *
     * @param id the id of the cotizacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cotizacions/{id}")
    public ResponseEntity<Void> deleteCotizacion(@PathVariable Long id) {
        log.debug("REST request to delete Cotizacion : {}", id);
        cotizacionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
