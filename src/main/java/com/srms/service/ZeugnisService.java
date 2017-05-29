package com.srms.service;

import com.srms.service.dto.ZeugnisDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Zeugnis.
 */
public interface ZeugnisService {

    /**
     * Save a zeugnis.
     *
     * @param zeugnisDTO the entity to save
     * @return the persisted entity
     */
    ZeugnisDTO save(ZeugnisDTO zeugnisDTO);

    /**
     *  Get all the zeugnis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ZeugnisDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" zeugnis.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ZeugnisDTO findOne(Long id);

    /**
     *  Delete the "id" zeugnis.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
