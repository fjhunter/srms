package com.srms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.srms.domain.Schueler;
import com.srms.repository.SchuelerRepository;
import com.srms.service.SchuelerService;
import com.srms.web.rest.util.HeaderUtil;
import com.srms.service.dto.SchuelerDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Schueler.
 */
@RestController
@RequestMapping("/api")
public class SchuelerResource {

    private final Logger log = LoggerFactory.getLogger(SchuelerResource.class);

    private static final String ENTITY_NAME = "schueler";

    private final SchuelerService schuelerService;

    private final SchuelerRepository schuelerRepository;

    public SchuelerResource(SchuelerService schuelerService, SchuelerRepository schuelerRepository) {
        this.schuelerService = schuelerService;
        this.schuelerRepository = schuelerRepository;
    }

    /**
     * POST  /schuelers : Create a new schueler.
     *
     * @param schuelerDTO the schuelerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schuelerDTO, or with status 400 (Bad Request) if the schueler has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schuelers")
    @Timed
    public ResponseEntity<SchuelerDTO> createSchueler(@Valid @RequestBody SchuelerDTO schuelerDTO) throws URISyntaxException {
        log.debug("REST request to save Schueler : {}", schuelerDTO);
        if (schuelerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schueler cannot already have an ID")).body(null);
        }
        SchuelerDTO result = schuelerService.save(schuelerDTO);
        return ResponseEntity.created(new URI("/api/schuelers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schuelers : Updates an existing schueler.
     *
     * @param schuelerDTO the schuelerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schuelerDTO,
     * or with status 400 (Bad Request) if the schuelerDTO is not valid,
     * or with status 500 (Internal Server Error) if the schuelerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schuelers")
    @Timed
    public ResponseEntity<SchuelerDTO> updateSchueler(@Valid @RequestBody SchuelerDTO schuelerDTO) throws URISyntaxException {
        log.debug("REST request to update Schueler : {}", schuelerDTO);
        if (schuelerDTO.getId() == null) {
            return createSchueler(schuelerDTO);
        }
        SchuelerDTO result = schuelerService.save(schuelerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schuelerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schuelers : get all the schuelers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schuelers in body
     */
    @GetMapping("/schuelers")
    @Timed
    public List<SchuelerDTO> getAllSchuelers() {
        log.debug("REST request to get all Schuelers");
        return schuelerService.findAll();
    }

    /**
     * GET  /schuelers/:id : get the "id" schueler.
     *
     * @param id the id of the schuelerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schuelerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/schuelers/{id}")
    @Timed
    public ResponseEntity<SchuelerDTO> getSchueler(@PathVariable Long id) {
        log.debug("REST request to get Schueler : {}", id);
        SchuelerDTO schuelerDTO = schuelerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schuelerDTO));
    }

    /**
     * DELETE  /schuelers/:id : delete the "id" schueler.
     *
     * @param id the id of the schuelerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schuelers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchueler(@PathVariable Long id) {
        log.debug("REST request to delete Schueler : {}", id);
        schuelerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping("schuelerFromLehrerId/{id}")
    public List<Schueler> getSchuelerFromLeherId(@PathVariable Long id) {
        List<Schueler> schueler = schuelerRepository.findByKlasse_KlasseFachesLehrerId(id);
        return schueler;
    }
}
