package com.srms.service.impl;

import com.srms.service.KlasseFachService;
import com.srms.domain.KlasseFach;
import com.srms.repository.KlasseFachRepository;
import com.srms.service.dto.KlasseFachDTO;
import com.srms.service.mapper.KlasseFachMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing KlasseFach.
 */
@Service
@Transactional
public class KlasseFachServiceImpl implements KlasseFachService{

    private final Logger log = LoggerFactory.getLogger(KlasseFachServiceImpl.class);
    
    private final KlasseFachRepository klasseFachRepository;

    private final KlasseFachMapper klasseFachMapper;

    public KlasseFachServiceImpl(KlasseFachRepository klasseFachRepository, KlasseFachMapper klasseFachMapper) {
        this.klasseFachRepository = klasseFachRepository;
        this.klasseFachMapper = klasseFachMapper;
    }

    /**
     * Save a klasseFach.
     *
     * @param klasseFachDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KlasseFachDTO save(KlasseFachDTO klasseFachDTO) {
        log.debug("Request to save KlasseFach : {}", klasseFachDTO);
        KlasseFach klasseFach = klasseFachMapper.toEntity(klasseFachDTO);
        klasseFach = klasseFachRepository.save(klasseFach);
        KlasseFachDTO result = klasseFachMapper.toDto(klasseFach);
        return result;
    }

    /**
     *  Get all the klasseFaches.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KlasseFachDTO> findAll() {
        log.debug("Request to get all KlasseFaches");
        List<KlasseFachDTO> result = klasseFachRepository.findAll().stream()
            .map(klasseFachMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one klasseFach by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KlasseFachDTO findOne(Long id) {
        log.debug("Request to get KlasseFach : {}", id);
        KlasseFach klasseFach = klasseFachRepository.findOne(id);
        KlasseFachDTO klasseFachDTO = klasseFachMapper.toDto(klasseFach);
        return klasseFachDTO;
    }

    /**
     *  Delete the  klasseFach by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KlasseFach : {}", id);
        klasseFachRepository.delete(id);
    }
}
