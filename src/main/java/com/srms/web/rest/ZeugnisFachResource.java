package com.srms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.srms.service.ZeugnisFachService;
import com.srms.web.rest.util.HeaderUtil;
import com.srms.service.dto.ZeugnisFachDTO;
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
 * REST controller for managing ZeugnisFach.
 */
@RestController
@RequestMapping("/api")
public class ZeugnisFachResource {

    private final Logger log = LoggerFactory.getLogger(ZeugnisFachResource.class);

    private static final String ENTITY_NAME = "zeugnisFach";
        
    private final ZeugnisFachService zeugnisFachService;

    public ZeugnisFachResource(ZeugnisFachService zeugnisFachService) {
        this.zeugnisFachService = zeugnisFachService;
    }

    /**
     * POST  /zeugnis-faches : Create a new zeugnisFach.
     *
     * @param zeugnisFachDTO the zeugnisFachDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zeugnisFachDTO, or with status 400 (Bad Request) if the zeugnisFach has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zeugnis-faches")
    @Timed
    public ResponseEntity<ZeugnisFachDTO> createZeugnisFach(@RequestBody ZeugnisFachDTO zeugnisFachDTO) throws URISyntaxException {
        log.debug("REST request to save ZeugnisFach : {}", zeugnisFachDTO);
        if (zeugnisFachDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new zeugnisFach cannot already have an ID")).body(null);
        }
        ZeugnisFachDTO result = zeugnisFachService.save(zeugnisFachDTO);
        return ResponseEntity.created(new URI("/api/zeugnis-faches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zeugnis-faches : Updates an existing zeugnisFach.
     *
     * @param zeugnisFachDTO the zeugnisFachDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zeugnisFachDTO,
     * or with status 400 (Bad Request) if the zeugnisFachDTO is not valid,
     * or with status 500 (Internal Server Error) if the zeugnisFachDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zeugnis-faches")
    @Timed
    public ResponseEntity<ZeugnisFachDTO> updateZeugnisFach(@RequestBody ZeugnisFachDTO zeugnisFachDTO) throws URISyntaxException {
        log.debug("REST request to update ZeugnisFach : {}", zeugnisFachDTO);
        if (zeugnisFachDTO.getId() == null) {
            return createZeugnisFach(zeugnisFachDTO);
        }
        ZeugnisFachDTO result = zeugnisFachService.save(zeugnisFachDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zeugnisFachDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zeugnis-faches : get all the zeugnisFaches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of zeugnisFaches in body
     */
    @GetMapping("/zeugnis-faches")
    @Timed
    public List<ZeugnisFachDTO> getAllZeugnisFaches() {
        log.debug("REST request to get all ZeugnisFaches");
        return zeugnisFachService.findAll();
    }

    /**
     * GET  /zeugnis-faches/:id : get the "id" zeugnisFach.
     *
     * @param id the id of the zeugnisFachDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zeugnisFachDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zeugnis-faches/{id}")
    @Timed
    public ResponseEntity<ZeugnisFachDTO> getZeugnisFach(@PathVariable Long id) {
        log.debug("REST request to get ZeugnisFach : {}", id);
        ZeugnisFachDTO zeugnisFachDTO = zeugnisFachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(zeugnisFachDTO));
    }

    /**
     * DELETE  /zeugnis-faches/:id : delete the "id" zeugnisFach.
     *
     * @param id the id of the zeugnisFachDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zeugnis-faches/{id}")
    @Timed
    public ResponseEntity<Void> deleteZeugnisFach(@PathVariable Long id) {
        log.debug("REST request to delete ZeugnisFach : {}", id);
        zeugnisFachService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
