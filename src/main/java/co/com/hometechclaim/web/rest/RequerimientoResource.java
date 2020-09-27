package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.Requerimiento;
import co.com.hometechclaim.domain.Solucion;
import co.com.hometechclaim.domain.enumeration.EstadoRequerimiento;
import co.com.hometechclaim.domain.enumeration.TipoRequerimiento;
import co.com.hometechclaim.repository.RequerimientoRepository;
import co.com.hometechclaim.repository.RequerimientoToSolucionRepository;
import co.com.hometechclaim.repository.SolucionRepository;
import co.com.hometechclaim.security.SecurityUtils;
import co.com.hometechclaim.service.dto.requerimiento.RequerimientoToSolucion;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.com.hometechclaim.domain.Requerimiento}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RequerimientoResource {

    private final Logger log = LoggerFactory.getLogger(RequerimientoResource.class);

    private static final String ENTITY_NAME = "requerimiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequerimientoRepository requerimientoRepository;
    private final SolucionRepository solucionRepository;
    private final RequerimientoToSolucionRepository requerimientoToSolucionRepository;

    public RequerimientoResource(RequerimientoRepository requerimientoRepository,SolucionRepository solucionRepository,RequerimientoToSolucionRepository requerimientoToSolucionRepository) {
        this.requerimientoRepository = requerimientoRepository;
        this.solucionRepository = solucionRepository;
        this.requerimientoToSolucionRepository= requerimientoToSolucionRepository;
    }

    /**
     * {@code POST  /requerimientos} : Create a new requerimiento.
     *
     * @param requerimiento the requerimiento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requerimiento, or with status {@code 400 (Bad Request)} if the requerimiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requerimientos")
    public ResponseEntity<Requerimiento> createRequerimiento(@Valid @RequestBody Requerimiento requerimiento) throws URISyntaxException {
        log.debug("REST request to save Requerimiento : {}", requerimiento);
        if (requerimiento.getId() != null) {
            throw new BadRequestAlertException("A new requerimiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Requerimiento result = requerimientoRepository.save(requerimiento);
        return ResponseEntity.created(new URI("/api/requerimientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code POST  /requerimientos} : Create a new requerimiento.
     *
     * @param requerimiento the requerimiento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requerimiento, or with status {@code 400 (Bad Request)} if the requerimiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requerimientos-create-solucion")
    public ResponseEntity<String> createRequerimientoCreateSolucion(@Valid @RequestBody RequerimientoToSolucion requerimiento) throws URISyntaxException {
        log.debug("REST request to save createRequerimientoCreateSolucion : {}", requerimiento);
        
        Requerimiento requerimientoEntity = new Requerimiento();
        requerimientoEntity.setDetalleproblema(requerimiento.getObservacion());
        requerimientoEntity.setFechacreacion(Instant.now());
        requerimientoEntity.setIdUsuario(SecurityUtils.getCurrentUserLogin().get());
        requerimientoEntity.setTipoRequerimiento(TipoRequerimiento.PROBLEMASEQUIPO);
        requerimientoEntity.setEstadoRequerimiento(EstadoRequerimiento.INICIADO);
        
        requerimientoEntity = requerimientoRepository.save(requerimientoEntity);
        List<co.com.hometechclaim.domain.RequerimientoToSolucion> listRequerimientoToSolucion = new ArrayList<>();
        for (String idSolucione : requerimiento.getIdSoluciones()) {
            co.com.hometechclaim.domain.RequerimientoToSolucion requerimientoToSolucion = new co.com.hometechclaim.domain.RequerimientoToSolucion();
            requerimientoToSolucion.setSolucion(solucionRepository.findById(Long.parseLong(idSolucione)).get());
            requerimientoToSolucion.setRequerimiento(requerimientoEntity);
            requerimientoToSolucion.setFechacreacion(Instant.now());
            listRequerimientoToSolucion.add(requerimientoToSolucion);
        }
        requerimientoToSolucionRepository.saveAll(listRequerimientoToSolucion);
        
        
        System.out.println(SecurityUtils.getCurrentUserLogin());
        
        return ResponseEntity.created(new URI("/api/requerimientos/" + requerimientoEntity.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, requerimientoEntity.getId().toString()))
            .body("Ok");
    }

    /**
     * {@code POST  /requerimientos} : Create a new requerimiento.
     *
     * @param requerimiento the requerimiento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requerimiento, or with status {@code 400 (Bad Request)} if the requerimiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requerimientos-create-solucion-new")
    public ResponseEntity<String> createRequerimientoCreateSolucionNew(@Valid @RequestBody RequerimientoToSolucion requerimiento) throws URISyntaxException {
        log.debug("REST request to save createRequerimientoCreateSolucion : {}", requerimiento);
        
        Requerimiento requerimientoEntity = new Requerimiento();
        requerimientoEntity.setDetalleproblema(requerimiento.getObservacion());
        requerimientoEntity.setFechacreacion(Instant.now());
        requerimientoEntity.setIdUsuario(SecurityUtils.getCurrentUserLogin().get());
        requerimientoEntity.setTipoRequerimiento(TipoRequerimiento.NUEVOEQUIPO);
        requerimientoEntity.setEstadoRequerimiento(EstadoRequerimiento.INICIADO);
        
        requerimientoEntity = requerimientoRepository.save(requerimientoEntity);
        List<co.com.hometechclaim.domain.RequerimientoToSolucion> listRequerimientoToSolucion = new ArrayList<>();
        for (String idSolucione : requerimiento.getIdSoluciones()) {
            co.com.hometechclaim.domain.RequerimientoToSolucion requerimientoToSolucion = new co.com.hometechclaim.domain.RequerimientoToSolucion();
            requerimientoToSolucion.setSolucion(solucionRepository.findById(Long.parseLong(idSolucione)).get());
            requerimientoToSolucion.setRequerimiento(requerimientoEntity);
            requerimientoToSolucion.setFechacreacion(Instant.now());
            listRequerimientoToSolucion.add(requerimientoToSolucion);
        }
        requerimientoToSolucionRepository.saveAll(listRequerimientoToSolucion);
        
        
        System.out.println(SecurityUtils.getCurrentUserLogin());
        
        return ResponseEntity.created(new URI("/api/requerimientos/" + requerimientoEntity.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, requerimientoEntity.getId().toString()))
            .body("Ok");
    }

    /**
     * {@code PUT  /requerimientos} : Updates an existing requerimiento.
     *
     * @param requerimiento the requerimiento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requerimiento,
     * or with status {@code 400 (Bad Request)} if the requerimiento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requerimiento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requerimientos")
    public ResponseEntity<Requerimiento> updateRequerimiento(@Valid @RequestBody Requerimiento requerimiento) throws URISyntaxException {
        log.debug("REST request to update Requerimiento : {}", requerimiento);
        if (requerimiento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Requerimiento result = requerimientoRepository.save(requerimiento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requerimiento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /requerimientos} : get all the requerimientos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requerimientos in body.
     */
    @GetMapping("/requerimientos")
    public ResponseEntity<List<Requerimiento>> getAllRequerimientos(Pageable pageable) {
        log.debug("REST request to get a page of Requerimientos");
        Page<Requerimiento> page = requerimientoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /requerimientos/:id} : get the "id" requerimiento.
     *
     * @param id the id of the requerimiento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requerimiento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requerimientos/{id}")
    public ResponseEntity<Requerimiento> getRequerimiento(@PathVariable Long id) {
        log.debug("REST request to get Requerimiento : {}", id);
        Optional<Requerimiento> requerimiento = requerimientoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requerimiento);
    }

    /**
     * {@code DELETE  /requerimientos/:id} : delete the "id" requerimiento.
     *
     * @param id the id of the requerimiento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requerimientos/{id}")
    public ResponseEntity<Void> deleteRequerimiento(@PathVariable Long id) {
        log.debug("REST request to delete Requerimiento : {}", id);
        requerimientoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
