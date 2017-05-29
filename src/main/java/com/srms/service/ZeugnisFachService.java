package com.srms.service;

import com.srms.service.dto.ZeugnisFachDTO;
import java.util.List;

/**
 * Service Interface for managing ZeugnisFach.
 */
public interface ZeugnisFachService {

    /**
     * Save a zeugnisFach.
     *
     * @param zeugnisFachDTO the entity to save
     * @return the persisted entity
     */
    ZeugnisFachDTO save(ZeugnisFachDTO zeugnisFachDTO);

    /**
     *  Get all the zeugnisFaches.
     *  
     *  @return the list of entities
     */
    List<ZeugnisFachDTO> findAll();

    /**
     *  Get the "id" zeugnisFach.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ZeugnisFachDTO findOne(Long id);

    /**
     *  Delete the "id" zeugnisFach.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
