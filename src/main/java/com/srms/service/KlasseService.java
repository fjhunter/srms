package com.srms.service;

import com.srms.service.dto.KlasseDTO;
import java.util.List;

/**
 * Service Interface for managing Klasse.
 */
public interface KlasseService {

    /**
     * Save a klasse.
     *
     * @param klasseDTO the entity to save
     * @return the persisted entity
     */
    KlasseDTO save(KlasseDTO klasseDTO);

    /**
     *  Get all the klasses.
     *  
     *  @return the list of entities
     */
    List<KlasseDTO> findAll();

    /**
     *  Get the "id" klasse.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    KlasseDTO findOne(Long id);

    /**
     *  Delete the "id" klasse.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
