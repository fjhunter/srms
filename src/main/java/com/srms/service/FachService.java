package com.srms.service;

import com.srms.service.dto.FachDTO;
import java.util.List;

/**
 * Service Interface for managing Fach.
 */
public interface FachService {

    /**
     * Save a fach.
     *
     * @param fachDTO the entity to save
     * @return the persisted entity
     */
    FachDTO save(FachDTO fachDTO);

    /**
     *  Get all the faches.
     *  
     *  @return the list of entities
     */
    List<FachDTO> findAll();

    /**
     *  Get the "id" fach.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FachDTO findOne(Long id);

    /**
     *  Delete the "id" fach.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
