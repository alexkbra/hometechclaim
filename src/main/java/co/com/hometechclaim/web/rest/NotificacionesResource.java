package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.Notificaciones;
import co.com.hometechclaim.repository.NotificacionesRepository;
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
 * REST controller for managing {@link co.com.hometechclaim.domain.Notificaciones}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NotificacionesResource {

    private final Logger log = LoggerFactory.getLogger(NotificacionesResource.class);

    private static final String ENTITY_NAME = "notificaciones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificacionesRepository notificacionesRepository;

    public NotificacionesResource(NotificacionesRepository notificacionesRepository) {
        this.notificacionesRepository = notificacionesRepository;
    }

    /**
     * {@code POST  /notificaciones} : Create a new notificaciones.
     *
     * @param notificaciones the notificaciones to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificaciones, or with status {@code 400 (Bad Request)} if the notificaciones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notificaciones")
    public ResponseEntity<Notificaciones> createNotificaciones(@Valid @RequestBody Notificaciones notificaciones) throws URISyntaxException {
        log.debug("REST request to save Notificaciones : {}", notificaciones);
        if (notificaciones.getId() != null) {
            throw new BadRequestAlertException("A new notificaciones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Notificaciones result = notificacionesRepository.save(notificaciones);
        return ResponseEntity.created(new URI("/api/notificaciones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notificaciones} : Updates an existing notificaciones.
     *
     * @param notificaciones the notificaciones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificaciones,
     * or with status {@code 400 (Bad Request)} if the notificaciones is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificaciones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notificaciones")
    public ResponseEntity<Notificaciones> updateNotificaciones(@Valid @RequestBody Notificaciones notificaciones) throws URISyntaxException {
        log.debug("REST request to update Notificaciones : {}", notificaciones);
        if (notificaciones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Notificaciones result = notificacionesRepository.save(notificaciones);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificaciones.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notificaciones} : get all the notificaciones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificaciones in body.
     */
    @GetMapping("/notificaciones")
    public ResponseEntity<List<Notificaciones>> getAllNotificaciones(Pageable pageable) {
        log.debug("REST request to get a page of Notificaciones");
        Page<Notificaciones> page = notificacionesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notificaciones/:id} : get the "id" notificaciones.
     *
     * @param id the id of the notificaciones to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificaciones, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notificaciones/{id}")
    public ResponseEntity<Notificaciones> getNotificaciones(@PathVariable Long id) {
        log.debug("REST request to get Notificaciones : {}", id);
        Optional<Notificaciones> notificaciones = notificacionesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notificaciones);
    }

    /**
     * {@code DELETE  /notificaciones/:id} : delete the "id" notificaciones.
     *
     * @param id the id of the notificaciones to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notificaciones/{id}")
    public ResponseEntity<Void> deleteNotificaciones(@PathVariable Long id) {
        log.debug("REST request to delete Notificaciones : {}", id);
        notificacionesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
