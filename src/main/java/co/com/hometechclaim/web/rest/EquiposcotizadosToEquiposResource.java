package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.EquiposcotizadosToEquipos;
import co.com.hometechclaim.repository.EquiposcotizadosToEquiposRepository;
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
 * REST controller for managing {@link co.com.hometechclaim.domain.EquiposcotizadosToEquipos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EquiposcotizadosToEquiposResource {

    private final Logger log = LoggerFactory.getLogger(EquiposcotizadosToEquiposResource.class);

    private static final String ENTITY_NAME = "equiposcotizadosToEquipos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquiposcotizadosToEquiposRepository equiposcotizadosToEquiposRepository;

    public EquiposcotizadosToEquiposResource(EquiposcotizadosToEquiposRepository equiposcotizadosToEquiposRepository) {
        this.equiposcotizadosToEquiposRepository = equiposcotizadosToEquiposRepository;
    }

    /**
     * {@code POST  /equiposcotizados-to-equipos} : Create a new equiposcotizadosToEquipos.
     *
     * @param equiposcotizadosToEquipos the equiposcotizadosToEquipos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equiposcotizadosToEquipos, or with status {@code 400 (Bad Request)} if the equiposcotizadosToEquipos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equiposcotizados-to-equipos")
    public ResponseEntity<EquiposcotizadosToEquipos> createEquiposcotizadosToEquipos(@Valid @RequestBody EquiposcotizadosToEquipos equiposcotizadosToEquipos) throws URISyntaxException {
        log.debug("REST request to save EquiposcotizadosToEquipos : {}", equiposcotizadosToEquipos);
        if (equiposcotizadosToEquipos.getId() != null) {
            throw new BadRequestAlertException("A new equiposcotizadosToEquipos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquiposcotizadosToEquipos result = equiposcotizadosToEquiposRepository.save(equiposcotizadosToEquipos);
        return ResponseEntity.created(new URI("/api/equiposcotizados-to-equipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equiposcotizados-to-equipos} : Updates an existing equiposcotizadosToEquipos.
     *
     * @param equiposcotizadosToEquipos the equiposcotizadosToEquipos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equiposcotizadosToEquipos,
     * or with status {@code 400 (Bad Request)} if the equiposcotizadosToEquipos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equiposcotizadosToEquipos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equiposcotizados-to-equipos")
    public ResponseEntity<EquiposcotizadosToEquipos> updateEquiposcotizadosToEquipos(@Valid @RequestBody EquiposcotizadosToEquipos equiposcotizadosToEquipos) throws URISyntaxException {
        log.debug("REST request to update EquiposcotizadosToEquipos : {}", equiposcotizadosToEquipos);
        if (equiposcotizadosToEquipos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EquiposcotizadosToEquipos result = equiposcotizadosToEquiposRepository.save(equiposcotizadosToEquipos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equiposcotizadosToEquipos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equiposcotizados-to-equipos} : get all the equiposcotizadosToEquipos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equiposcotizadosToEquipos in body.
     */
    @GetMapping("/equiposcotizados-to-equipos")
    public ResponseEntity<List<EquiposcotizadosToEquipos>> getAllEquiposcotizadosToEquipos(Pageable pageable) {
        log.debug("REST request to get a page of EquiposcotizadosToEquipos");
        Page<EquiposcotizadosToEquipos> page = equiposcotizadosToEquiposRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equiposcotizados-to-equipos/:id} : get the "id" equiposcotizadosToEquipos.
     *
     * @param id the id of the equiposcotizadosToEquipos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equiposcotizadosToEquipos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equiposcotizados-to-equipos/{id}")
    public ResponseEntity<EquiposcotizadosToEquipos> getEquiposcotizadosToEquipos(@PathVariable Long id) {
        log.debug("REST request to get EquiposcotizadosToEquipos : {}", id);
        Optional<EquiposcotizadosToEquipos> equiposcotizadosToEquipos = equiposcotizadosToEquiposRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equiposcotizadosToEquipos);
    }

    /**
     * {@code DELETE  /equiposcotizados-to-equipos/:id} : delete the "id" equiposcotizadosToEquipos.
     *
     * @param id the id of the equiposcotizadosToEquipos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equiposcotizados-to-equipos/{id}")
    public ResponseEntity<Void> deleteEquiposcotizadosToEquipos(@PathVariable Long id) {
        log.debug("REST request to delete EquiposcotizadosToEquipos : {}", id);
        equiposcotizadosToEquiposRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
