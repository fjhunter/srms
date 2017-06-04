package com.srms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.srms.repository.ZeugnisRepository;
import com.srms.service.ZeugnisService;
import com.srms.service.dto.ZeugnisDTO;
import com.srms.service.impl.RealZeugnisService;
import com.srms.web.rest.util.HeaderUtil;
import com.srms.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Zeugnis.
 */
@RestController
@RequestMapping("/api")
public class ZeugnisResource {

    private final Logger log = LoggerFactory.getLogger(ZeugnisResource.class);

    private static final String ENTITY_NAME = "zeugnis";

    private final RealZeugnisService realZeugnisService;

    private final ZeugnisService zeugnisService;

    private final ZeugnisRepository zeugnisRepository;


    public ZeugnisResource(ZeugnisService zeugnisService, ZeugnisRepository zeugnisRepository, RealZeugnisService realZeugnisService) {
        this.zeugnisService = zeugnisService;
        this.zeugnisRepository = zeugnisRepository;
        this.realZeugnisService = realZeugnisService;
    }

    /**
     * POST  /zeugnis : Create a new zeugnis.
     *
     * @param zeugnisDTO the zeugnisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zeugnisDTO, or with status 400 (Bad Request) if the zeugnis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zeugnis")
    @Timed
    public ResponseEntity<ZeugnisDTO> createZeugnis(@Valid @RequestBody ZeugnisDTO zeugnisDTO) throws URISyntaxException {
        log.debug("REST request to save Zeugnis : {}", zeugnisDTO);
        if (zeugnisDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new zeugnis cannot already have an ID")).body(null);
        }
        ZeugnisDTO result = zeugnisService.save(zeugnisDTO);
        return ResponseEntity.created(new URI("/api/zeugnis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zeugnis : Updates an existing zeugnis.
     *
     * @param zeugnisDTO the zeugnisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zeugnisDTO,
     * or with status 400 (Bad Request) if the zeugnisDTO is not valid,
     * or with status 500 (Internal Server Error) if the zeugnisDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zeugnis")
    @Timed
    public ResponseEntity<ZeugnisDTO> updateZeugnis(@Valid @RequestBody ZeugnisDTO zeugnisDTO) throws URISyntaxException {
        log.debug("REST request to update Zeugnis : {}", zeugnisDTO);
        if (zeugnisDTO.getId() == null) {
            return createZeugnis(zeugnisDTO);
        }
        ZeugnisDTO result = zeugnisService.save(zeugnisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zeugnisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zeugnis : get all the zeugnis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of zeugnis in body
     */
    @GetMapping("/zeugnis")
    @Timed
    public ResponseEntity<List<ZeugnisDTO>> getAllZeugnis(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Zeugnis");
        Page<ZeugnisDTO> page = zeugnisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zeugnis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zeugnis/:id : get the "id" zeugnis.
     *
     * @param id the id of the zeugnisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zeugnisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zeugnis/{id}")
    @Timed
    public ResponseEntity<ZeugnisDTO> getZeugnis(@PathVariable Long id) {
        log.debug("REST request to get Zeugnis : {}", id);
        ZeugnisDTO zeugnisDTO = zeugnisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(zeugnisDTO));
    }

    /**
     * DELETE  /zeugnis/:id : delete the "id" zeugnis.
     *
     * @param id the id of the zeugnisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zeugnis/{id}")
    @Timed
    public ResponseEntity<Void> deleteZeugnis(@PathVariable Long id) {
        log.debug("REST request to delete Zeugnis : {}", id);
        zeugnisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(value = "/getZeugnisByDateTypeSchueler", method = RequestMethod.POST)
    @ResponseBody
    public List<RealZeugnis> getBySchuelerId(@RequestBody SchuelerDatumZeugnisTyp schuelerDatumZeugnisTyp) throws ParseException {
        schuelerDatumZeugnisTyp.setDatum(schuelerDatumZeugnisTyp.getDatum().replace("T", ""));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DDhh:mm");
        Date date = simpleDateFormat.parse(schuelerDatumZeugnisTyp.getDatum());
        ZonedDateTime zonedDate = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("Europe/Berlin"));
        return realZeugnisService.getRealZeugnise(schuelerDatumZeugnisTyp.getZeugnisTyp(), schuelerDatumZeugnisTyp.getLehrerId(), zonedDate);
    }

}
