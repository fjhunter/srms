package com.srms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.srms.service.KlasseFachService;
import com.srms.web.rest.util.HeaderUtil;
import com.srms.service.dto.KlasseFachDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing KlasseFach.
 */
@RestController
@RequestMapping("/api")
public class KlasseFachResource {

    private final Logger log = LoggerFactory.getLogger(KlasseFachResource.class);

    private static final String ENTITY_NAME = "klasseFach";
        
    private final KlasseFachService klasseFachService;

    public KlasseFachResource(KlasseFachService klasseFachService) {
        this.klasseFachService = klasseFachService;
    }

    /**
     * POST  /klasse-faches : Create a new klasseFach.
     *
     * @param klasseFachDTO the klasseFachDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new klasseFachDTO, or with status 400 (Bad Request) if the klasseFach has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/klasse-faches")
    @Timed
    public ResponseEntity<KlasseFachDTO> createKlasseFach(@RequestBody KlasseFachDTO klasseFachDTO) throws URISyntaxException {
        log.debug("REST request to save KlasseFach : {}", klasseFachDTO);
        if (klasseFachDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new klasseFach cannot already have an ID")).body(null);
        }
        KlasseFachDTO result = klasseFachService.save(klasseFachDTO);
        return ResponseEntity.created(new URI("/api/klasse-faches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /klasse-faches : Updates an existing klasseFach.
     *
     * @param klasseFachDTO the klasseFachDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated klasseFachDTO,
     * or with status 400 (Bad Request) if the klasseFachDTO is not valid,
     * or with status 500 (Internal Server Error) if the klasseFachDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/klasse-faches")
    @Timed
    public ResponseEntity<KlasseFachDTO> updateKlasseFach(@RequestBody KlasseFachDTO klasseFachDTO) throws URISyntaxException {
        log.debug("REST request to update KlasseFach : {}", klasseFachDTO);
        if (klasseFachDTO.getId() == null) {
            return createKlasseFach(klasseFachDTO);
        }
        KlasseFachDTO result = klasseFachService.save(klasseFachDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, klasseFachDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /klasse-faches : get all the klasseFaches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of klasseFaches in body
     */
    @GetMapping("/klasse-faches")
    @Timed
    public List<KlasseFachDTO> getAllKlasseFaches() {
        log.debug("REST request to get all KlasseFaches");
        return klasseFachService.findAll();
    }

    /**
     * GET  /klasse-faches/:id : get the "id" klasseFach.
     *
     * @param id the id of the klasseFachDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the klasseFachDTO, or with status 404 (Not Found)
     */
    @GetMapping("/klasse-faches/{id}")
    @Timed
    public ResponseEntity<KlasseFachDTO> getKlasseFach(@PathVariable Long id) {
        log.debug("REST request to get KlasseFach : {}", id);
        KlasseFachDTO klasseFachDTO = klasseFachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(klasseFachDTO));
    }

    /**
     * DELETE  /klasse-faches/:id : delete the "id" klasseFach.
     *
     * @param id the id of the klasseFachDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/klasse-faches/{id}")
    @Timed
    public ResponseEntity<Void> deleteKlasseFach(@PathVariable Long id) {
        log.debug("REST request to delete KlasseFach : {}", id);
        klasseFachService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
