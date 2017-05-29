package com.srms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.srms.service.AnschriftService;
import com.srms.web.rest.util.HeaderUtil;
import com.srms.service.dto.AnschriftDTO;
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
 * REST controller for managing Anschrift.
 */
@RestController
@RequestMapping("/api")
public class AnschriftResource {

    private final Logger log = LoggerFactory.getLogger(AnschriftResource.class);

    private static final String ENTITY_NAME = "anschrift";
        
    private final AnschriftService anschriftService;

    public AnschriftResource(AnschriftService anschriftService) {
        this.anschriftService = anschriftService;
    }

    /**
     * POST  /anschrifts : Create a new anschrift.
     *
     * @param anschriftDTO the anschriftDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anschriftDTO, or with status 400 (Bad Request) if the anschrift has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/anschrifts")
    @Timed
    public ResponseEntity<AnschriftDTO> createAnschrift(@Valid @RequestBody AnschriftDTO anschriftDTO) throws URISyntaxException {
        log.debug("REST request to save Anschrift : {}", anschriftDTO);
        if (anschriftDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new anschrift cannot already have an ID")).body(null);
        }
        AnschriftDTO result = anschriftService.save(anschriftDTO);
        return ResponseEntity.created(new URI("/api/anschrifts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anschrifts : Updates an existing anschrift.
     *
     * @param anschriftDTO the anschriftDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anschriftDTO,
     * or with status 400 (Bad Request) if the anschriftDTO is not valid,
     * or with status 500 (Internal Server Error) if the anschriftDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/anschrifts")
    @Timed
    public ResponseEntity<AnschriftDTO> updateAnschrift(@Valid @RequestBody AnschriftDTO anschriftDTO) throws URISyntaxException {
        log.debug("REST request to update Anschrift : {}", anschriftDTO);
        if (anschriftDTO.getId() == null) {
            return createAnschrift(anschriftDTO);
        }
        AnschriftDTO result = anschriftService.save(anschriftDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anschriftDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /anschrifts : get all the anschrifts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of anschrifts in body
     */
    @GetMapping("/anschrifts")
    @Timed
    public List<AnschriftDTO> getAllAnschrifts() {
        log.debug("REST request to get all Anschrifts");
        return anschriftService.findAll();
    }

    /**
     * GET  /anschrifts/:id : get the "id" anschrift.
     *
     * @param id the id of the anschriftDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anschriftDTO, or with status 404 (Not Found)
     */
    @GetMapping("/anschrifts/{id}")
    @Timed
    public ResponseEntity<AnschriftDTO> getAnschrift(@PathVariable Long id) {
        log.debug("REST request to get Anschrift : {}", id);
        AnschriftDTO anschriftDTO = anschriftService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(anschriftDTO));
    }

    /**
     * DELETE  /anschrifts/:id : delete the "id" anschrift.
     *
     * @param id the id of the anschriftDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/anschrifts/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnschrift(@PathVariable Long id) {
        log.debug("REST request to delete Anschrift : {}", id);
        anschriftService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
