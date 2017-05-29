package com.srms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.srms.service.FehlzeitenService;
import com.srms.web.rest.util.HeaderUtil;
import com.srms.service.dto.FehlzeitenDTO;
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
 * REST controller for managing Fehlzeiten.
 */
@RestController
@RequestMapping("/api")
public class FehlzeitenResource {

    private final Logger log = LoggerFactory.getLogger(FehlzeitenResource.class);

    private static final String ENTITY_NAME = "fehlzeiten";
        
    private final FehlzeitenService fehlzeitenService;

    public FehlzeitenResource(FehlzeitenService fehlzeitenService) {
        this.fehlzeitenService = fehlzeitenService;
    }

    /**
     * POST  /fehlzeitens : Create a new fehlzeiten.
     *
     * @param fehlzeitenDTO the fehlzeitenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fehlzeitenDTO, or with status 400 (Bad Request) if the fehlzeiten has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fehlzeitens")
    @Timed
    public ResponseEntity<FehlzeitenDTO> createFehlzeiten(@Valid @RequestBody FehlzeitenDTO fehlzeitenDTO) throws URISyntaxException {
        log.debug("REST request to save Fehlzeiten : {}", fehlzeitenDTO);
        if (fehlzeitenDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fehlzeiten cannot already have an ID")).body(null);
        }
        FehlzeitenDTO result = fehlzeitenService.save(fehlzeitenDTO);
        return ResponseEntity.created(new URI("/api/fehlzeitens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fehlzeitens : Updates an existing fehlzeiten.
     *
     * @param fehlzeitenDTO the fehlzeitenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fehlzeitenDTO,
     * or with status 400 (Bad Request) if the fehlzeitenDTO is not valid,
     * or with status 500 (Internal Server Error) if the fehlzeitenDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fehlzeitens")
    @Timed
    public ResponseEntity<FehlzeitenDTO> updateFehlzeiten(@Valid @RequestBody FehlzeitenDTO fehlzeitenDTO) throws URISyntaxException {
        log.debug("REST request to update Fehlzeiten : {}", fehlzeitenDTO);
        if (fehlzeitenDTO.getId() == null) {
            return createFehlzeiten(fehlzeitenDTO);
        }
        FehlzeitenDTO result = fehlzeitenService.save(fehlzeitenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fehlzeitenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fehlzeitens : get all the fehlzeitens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fehlzeitens in body
     */
    @GetMapping("/fehlzeitens")
    @Timed
    public List<FehlzeitenDTO> getAllFehlzeitens() {
        log.debug("REST request to get all Fehlzeitens");
        return fehlzeitenService.findAll();
    }

    /**
     * GET  /fehlzeitens/:id : get the "id" fehlzeiten.
     *
     * @param id the id of the fehlzeitenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fehlzeitenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fehlzeitens/{id}")
    @Timed
    public ResponseEntity<FehlzeitenDTO> getFehlzeiten(@PathVariable Long id) {
        log.debug("REST request to get Fehlzeiten : {}", id);
        FehlzeitenDTO fehlzeitenDTO = fehlzeitenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fehlzeitenDTO));
    }

    /**
     * DELETE  /fehlzeitens/:id : delete the "id" fehlzeiten.
     *
     * @param id the id of the fehlzeitenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fehlzeitens/{id}")
    @Timed
    public ResponseEntity<Void> deleteFehlzeiten(@PathVariable Long id) {
        log.debug("REST request to delete Fehlzeiten : {}", id);
        fehlzeitenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
