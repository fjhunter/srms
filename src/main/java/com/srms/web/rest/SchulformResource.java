package com.srms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.srms.service.SchulformService;
import com.srms.web.rest.util.HeaderUtil;
import com.srms.service.dto.SchulformDTO;
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
 * REST controller for managing Schulform.
 */
@RestController
@RequestMapping("/api")
public class SchulformResource {

    private final Logger log = LoggerFactory.getLogger(SchulformResource.class);

    private static final String ENTITY_NAME = "schulform";
        
    private final SchulformService schulformService;

    public SchulformResource(SchulformService schulformService) {
        this.schulformService = schulformService;
    }

    /**
     * POST  /schulforms : Create a new schulform.
     *
     * @param schulformDTO the schulformDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schulformDTO, or with status 400 (Bad Request) if the schulform has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schulforms")
    @Timed
    public ResponseEntity<SchulformDTO> createSchulform(@Valid @RequestBody SchulformDTO schulformDTO) throws URISyntaxException {
        log.debug("REST request to save Schulform : {}", schulformDTO);
        if (schulformDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schulform cannot already have an ID")).body(null);
        }
        SchulformDTO result = schulformService.save(schulformDTO);
        return ResponseEntity.created(new URI("/api/schulforms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schulforms : Updates an existing schulform.
     *
     * @param schulformDTO the schulformDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schulformDTO,
     * or with status 400 (Bad Request) if the schulformDTO is not valid,
     * or with status 500 (Internal Server Error) if the schulformDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schulforms")
    @Timed
    public ResponseEntity<SchulformDTO> updateSchulform(@Valid @RequestBody SchulformDTO schulformDTO) throws URISyntaxException {
        log.debug("REST request to update Schulform : {}", schulformDTO);
        if (schulformDTO.getId() == null) {
            return createSchulform(schulformDTO);
        }
        SchulformDTO result = schulformService.save(schulformDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schulformDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schulforms : get all the schulforms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schulforms in body
     */
    @GetMapping("/schulforms")
    @Timed
    public List<SchulformDTO> getAllSchulforms() {
        log.debug("REST request to get all Schulforms");
        return schulformService.findAll();
    }

    /**
     * GET  /schulforms/:id : get the "id" schulform.
     *
     * @param id the id of the schulformDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schulformDTO, or with status 404 (Not Found)
     */
    @GetMapping("/schulforms/{id}")
    @Timed
    public ResponseEntity<SchulformDTO> getSchulform(@PathVariable Long id) {
        log.debug("REST request to get Schulform : {}", id);
        SchulformDTO schulformDTO = schulformService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schulformDTO));
    }

    /**
     * DELETE  /schulforms/:id : delete the "id" schulform.
     *
     * @param id the id of the schulformDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schulforms/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchulform(@PathVariable Long id) {
        log.debug("REST request to delete Schulform : {}", id);
        schulformService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
