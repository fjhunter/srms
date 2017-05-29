package com.srms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.srms.service.FachService;
import com.srms.web.rest.util.HeaderUtil;
import com.srms.service.dto.FachDTO;
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
 * REST controller for managing Fach.
 */
@RestController
@RequestMapping("/api")
public class FachResource {

    private final Logger log = LoggerFactory.getLogger(FachResource.class);

    private static final String ENTITY_NAME = "fach";
        
    private final FachService fachService;

    public FachResource(FachService fachService) {
        this.fachService = fachService;
    }

    /**
     * POST  /faches : Create a new fach.
     *
     * @param fachDTO the fachDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fachDTO, or with status 400 (Bad Request) if the fach has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/faches")
    @Timed
    public ResponseEntity<FachDTO> createFach(@Valid @RequestBody FachDTO fachDTO) throws URISyntaxException {
        log.debug("REST request to save Fach : {}", fachDTO);
        if (fachDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fach cannot already have an ID")).body(null);
        }
        FachDTO result = fachService.save(fachDTO);
        return ResponseEntity.created(new URI("/api/faches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /faches : Updates an existing fach.
     *
     * @param fachDTO the fachDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fachDTO,
     * or with status 400 (Bad Request) if the fachDTO is not valid,
     * or with status 500 (Internal Server Error) if the fachDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/faches")
    @Timed
    public ResponseEntity<FachDTO> updateFach(@Valid @RequestBody FachDTO fachDTO) throws URISyntaxException {
        log.debug("REST request to update Fach : {}", fachDTO);
        if (fachDTO.getId() == null) {
            return createFach(fachDTO);
        }
        FachDTO result = fachService.save(fachDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fachDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /faches : get all the faches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of faches in body
     */
    @GetMapping("/faches")
    @Timed
    public List<FachDTO> getAllFaches() {
        log.debug("REST request to get all Faches");
        return fachService.findAll();
    }

    /**
     * GET  /faches/:id : get the "id" fach.
     *
     * @param id the id of the fachDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fachDTO, or with status 404 (Not Found)
     */
    @GetMapping("/faches/{id}")
    @Timed
    public ResponseEntity<FachDTO> getFach(@PathVariable Long id) {
        log.debug("REST request to get Fach : {}", id);
        FachDTO fachDTO = fachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fachDTO));
    }

    /**
     * DELETE  /faches/:id : delete the "id" fach.
     *
     * @param id the id of the fachDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/faches/{id}")
    @Timed
    public ResponseEntity<Void> deleteFach(@PathVariable Long id) {
        log.debug("REST request to delete Fach : {}", id);
        fachService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
