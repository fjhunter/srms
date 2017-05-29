package com.srms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.srms.service.KlasseService;
import com.srms.web.rest.util.HeaderUtil;
import com.srms.service.dto.KlasseDTO;
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
 * REST controller for managing Klasse.
 */
@RestController
@RequestMapping("/api")
public class KlasseResource {

    private final Logger log = LoggerFactory.getLogger(KlasseResource.class);

    private static final String ENTITY_NAME = "klasse";
        
    private final KlasseService klasseService;

    public KlasseResource(KlasseService klasseService) {
        this.klasseService = klasseService;
    }

    /**
     * POST  /klasses : Create a new klasse.
     *
     * @param klasseDTO the klasseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new klasseDTO, or with status 400 (Bad Request) if the klasse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/klasses")
    @Timed
    public ResponseEntity<KlasseDTO> createKlasse(@Valid @RequestBody KlasseDTO klasseDTO) throws URISyntaxException {
        log.debug("REST request to save Klasse : {}", klasseDTO);
        if (klasseDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new klasse cannot already have an ID")).body(null);
        }
        KlasseDTO result = klasseService.save(klasseDTO);
        return ResponseEntity.created(new URI("/api/klasses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /klasses : Updates an existing klasse.
     *
     * @param klasseDTO the klasseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated klasseDTO,
     * or with status 400 (Bad Request) if the klasseDTO is not valid,
     * or with status 500 (Internal Server Error) if the klasseDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/klasses")
    @Timed
    public ResponseEntity<KlasseDTO> updateKlasse(@Valid @RequestBody KlasseDTO klasseDTO) throws URISyntaxException {
        log.debug("REST request to update Klasse : {}", klasseDTO);
        if (klasseDTO.getId() == null) {
            return createKlasse(klasseDTO);
        }
        KlasseDTO result = klasseService.save(klasseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, klasseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /klasses : get all the klasses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of klasses in body
     */
    @GetMapping("/klasses")
    @Timed
    public List<KlasseDTO> getAllKlasses() {
        log.debug("REST request to get all Klasses");
        return klasseService.findAll();
    }

    /**
     * GET  /klasses/:id : get the "id" klasse.
     *
     * @param id the id of the klasseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the klasseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/klasses/{id}")
    @Timed
    public ResponseEntity<KlasseDTO> getKlasse(@PathVariable Long id) {
        log.debug("REST request to get Klasse : {}", id);
        KlasseDTO klasseDTO = klasseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(klasseDTO));
    }

    /**
     * DELETE  /klasses/:id : delete the "id" klasse.
     *
     * @param id the id of the klasseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/klasses/{id}")
    @Timed
    public ResponseEntity<Void> deleteKlasse(@PathVariable Long id) {
        log.debug("REST request to delete Klasse : {}", id);
        klasseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
