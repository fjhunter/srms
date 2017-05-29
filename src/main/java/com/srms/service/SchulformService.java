package com.srms.service;

import com.srms.service.dto.SchulformDTO;
import java.util.List;

/**
 * Service Interface for managing Schulform.
 */
public interface SchulformService {

    /**
     * Save a schulform.
     *
     * @param schulformDTO the entity to save
     * @return the persisted entity
     */
    SchulformDTO save(SchulformDTO schulformDTO);

    /**
     *  Get all the schulforms.
     *  
     *  @return the list of entities
     */
    List<SchulformDTO> findAll();

    /**
     *  Get the "id" schulform.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SchulformDTO findOne(Long id);

    /**
     *  Delete the "id" schulform.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
