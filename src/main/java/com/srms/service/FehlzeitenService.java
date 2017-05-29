package com.srms.service;

import com.srms.service.dto.FehlzeitenDTO;
import java.util.List;

/**
 * Service Interface for managing Fehlzeiten.
 */
public interface FehlzeitenService {

    /**
     * Save a fehlzeiten.
     *
     * @param fehlzeitenDTO the entity to save
     * @return the persisted entity
     */
    FehlzeitenDTO save(FehlzeitenDTO fehlzeitenDTO);

    /**
     *  Get all the fehlzeitens.
     *  
     *  @return the list of entities
     */
    List<FehlzeitenDTO> findAll();

    /**
     *  Get the "id" fehlzeiten.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FehlzeitenDTO findOne(Long id);

    /**
     *  Delete the "id" fehlzeiten.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
