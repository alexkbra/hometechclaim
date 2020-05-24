package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.Equiposinstalados;
import co.com.hometechclaim.repository.EquiposinstaladosRepository;
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
 * REST controller for managing {@link co.com.hometechclaim.domain.Equiposinstalados}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EquiposinstaladosResource {

    private final Logger log = LoggerFactory.getLogger(EquiposinstaladosResource.class);

    private static final String ENTITY_NAME = "equiposinstalados";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquiposinstaladosRepository equiposinstaladosRepository;

    public EquiposinstaladosResource(EquiposinstaladosRepository equiposinstaladosRepository) {
        this.equiposinstaladosRepository = equiposinstaladosRepository;
    }

    /**
     * {@code POST  /equiposinstalados} : Create a new equiposinstalados.
     *
     * @param equiposinstalados the equiposinstalados to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equiposinstalados, or with status {@code 400 (Bad Request)} if the equiposinstalados has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equiposinstalados")
    public ResponseEntity<Equiposinstalados> createEquiposinstalados(@Valid @RequestBody Equiposinstalados equiposinstalados) throws URISyntaxException {
        log.debug("REST request to save Equiposinstalados : {}", equiposinstalados);
        if (equiposinstalados.getId() != null) {
            throw new BadRequestAlertException("A new equiposinstalados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Equiposinstalados result = equiposinstaladosRepository.save(equiposinstalados);
        return ResponseEntity.created(new URI("/api/equiposinstalados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equiposinstalados} : Updates an existing equiposinstalados.
     *
     * @param equiposinstalados the equiposinstalados to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equiposinstalados,
     * or with status {@code 400 (Bad Request)} if the equiposinstalados is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equiposinstalados couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equiposinstalados")
    public ResponseEntity<Equiposinstalados> updateEquiposinstalados(@Valid @RequestBody Equiposinstalados equiposinstalados) throws URISyntaxException {
        log.debug("REST request to update Equiposinstalados : {}", equiposinstalados);
        if (equiposinstalados.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Equiposinstalados result = equiposinstaladosRepository.save(equiposinstalados);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equiposinstalados.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equiposinstalados} : get all the equiposinstalados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equiposinstalados in body.
     */
    @GetMapping("/equiposinstalados")
    public ResponseEntity<List<Equiposinstalados>> getAllEquiposinstalados(Pageable pageable) {
        log.debug("REST request to get a page of Equiposinstalados");
        Page<Equiposinstalados> page = equiposinstaladosRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equiposinstalados/:id} : get the "id" equiposinstalados.
     *
     * @param id the id of the equiposinstalados to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equiposinstalados, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equiposinstalados/{id}")
    public ResponseEntity<Equiposinstalados> getEquiposinstalados(@PathVariable Long id) {
        log.debug("REST request to get Equiposinstalados : {}", id);
        Optional<Equiposinstalados> equiposinstalados = equiposinstaladosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equiposinstalados);
    }

    /**
     * {@code DELETE  /equiposinstalados/:id} : delete the "id" equiposinstalados.
     *
     * @param id the id of the equiposinstalados to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equiposinstalados/{id}")
    public ResponseEntity<Void> deleteEquiposinstalados(@PathVariable Long id) {
        log.debug("REST request to delete Equiposinstalados : {}", id);
        equiposinstaladosRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
