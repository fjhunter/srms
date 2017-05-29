package com.srms.service;

import com.srms.service.dto.LehrerDTO;
import java.util.List;

/**
 * Service Interface for managing Lehrer.
 */
public interface LehrerService {

    /**
     * Save a lehrer.
     *
     * @param lehrerDTO the entity to save
     * @return the persisted entity
     */
    LehrerDTO save(LehrerDTO lehrerDTO);

    /**
     *  Get all the lehrers.
     *  
     *  @return the list of entities
     */
    List<LehrerDTO> findAll();

    /**
     *  Get the "id" lehrer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LehrerDTO findOne(Long id);

    /**
     *  Delete the "id" lehrer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
