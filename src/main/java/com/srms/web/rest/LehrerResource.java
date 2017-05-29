package com.srms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.srms.service.LehrerService;
import com.srms.web.rest.util.HeaderUtil;
import com.srms.service.dto.LehrerDTO;
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
 * REST controller for managing Lehrer.
 */
@RestController
@RequestMapping("/api")
public class LehrerResource {

    private final Logger log = LoggerFactory.getLogger(LehrerResource.class);

    private static final String ENTITY_NAME = "lehrer";
        
    private final LehrerService lehrerService;

    public LehrerResource(LehrerService lehrerService) {
        this.lehrerService = lehrerService;
    }

    /**
     * POST  /lehrers : Create a new lehrer.
     *
     * @param lehrerDTO the lehrerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lehrerDTO, or with status 400 (Bad Request) if the lehrer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lehrers")
    @Timed
    public ResponseEntity<LehrerDTO> createLehrer(@Valid @RequestBody LehrerDTO lehrerDTO) throws URISyntaxException {
        log.debug("REST request to save Lehrer : {}", lehrerDTO);
        if (lehrerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lehrer cannot already have an ID")).body(null);
        }
        LehrerDTO result = lehrerService.save(lehrerDTO);
        return ResponseEntity.created(new URI("/api/lehrers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lehrers : Updates an existing lehrer.
     *
     * @param lehrerDTO the lehrerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lehrerDTO,
     * or with status 400 (Bad Request) if the lehrerDTO is not valid,
     * or with status 500 (Internal Server Error) if the lehrerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lehrers")
    @Timed
    public ResponseEntity<LehrerDTO> updateLehrer(@Valid @RequestBody LehrerDTO lehrerDTO) throws URISyntaxException {
        log.debug("REST request to update Lehrer : {}", lehrerDTO);
        if (lehrerDTO.getId() == null) {
            return createLehrer(lehrerDTO);
        }
        LehrerDTO result = lehrerService.save(lehrerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lehrerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lehrers : get all the lehrers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lehrers in body
     */
    @GetMapping("/lehrers")
    @Timed
    public List<LehrerDTO> getAllLehrers() {
        log.debug("REST request to get all Lehrers");
        return lehrerService.findAll();
    }

    /**
     * GET  /lehrers/:id : get the "id" lehrer.
     *
     * @param id the id of the lehrerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lehrerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lehrers/{id}")
    @Timed
    public ResponseEntity<LehrerDTO> getLehrer(@PathVariable Long id) {
        log.debug("REST request to get Lehrer : {}", id);
        LehrerDTO lehrerDTO = lehrerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lehrerDTO));
    }

    /**
     * DELETE  /lehrers/:id : delete the "id" lehrer.
     *
     * @param id the id of the lehrerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lehrers/{id}")
    @Timed
    public ResponseEntity<Void> deleteLehrer(@PathVariable Long id) {
        log.debug("REST request to delete Lehrer : {}", id);
        lehrerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
