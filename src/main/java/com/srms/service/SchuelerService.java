package com.srms.service;

import com.srms.service.dto.SchuelerDTO;
import java.util.List;

/**
 * Service Interface for managing Schueler.
 */
public interface SchuelerService {

    /**
     * Save a schueler.
     *
     * @param schuelerDTO the entity to save
     * @return the persisted entity
     */
    SchuelerDTO save(SchuelerDTO schuelerDTO);

    /**
     *  Get all the schuelers.
     *  
     *  @return the list of entities
     */
    List<SchuelerDTO> findAll();

    /**
     *  Get the "id" schueler.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SchuelerDTO findOne(Long id);

    /**
     *  Delete the "id" schueler.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
