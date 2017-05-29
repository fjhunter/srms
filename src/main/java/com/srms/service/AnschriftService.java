package com.srms.service;

import com.srms.service.dto.AnschriftDTO;
import java.util.List;

/**
 * Service Interface for managing Anschrift.
 */
public interface AnschriftService {

    /**
     * Save a anschrift.
     *
     * @param anschriftDTO the entity to save
     * @return the persisted entity
     */
    AnschriftDTO save(AnschriftDTO anschriftDTO);

    /**
     *  Get all the anschrifts.
     *  
     *  @return the list of entities
     */
    List<AnschriftDTO> findAll();

    /**
     *  Get the "id" anschrift.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AnschriftDTO findOne(Long id);

    /**
     *  Delete the "id" anschrift.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
