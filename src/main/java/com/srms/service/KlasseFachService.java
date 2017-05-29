package com.srms.service;

import com.srms.service.dto.KlasseFachDTO;
import java.util.List;

/**
 * Service Interface for managing KlasseFach.
 */
public interface KlasseFachService {

    /**
     * Save a klasseFach.
     *
     * @param klasseFachDTO the entity to save
     * @return the persisted entity
     */
    KlasseFachDTO save(KlasseFachDTO klasseFachDTO);

    /**
     *  Get all the klasseFaches.
     *  
     *  @return the list of entities
     */
    List<KlasseFachDTO> findAll();

    /**
     *  Get the "id" klasseFach.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    KlasseFachDTO findOne(Long id);

    /**
     *  Delete the "id" klasseFach.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
