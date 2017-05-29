package com.srms.service.impl;

import com.srms.service.SchulformService;
import com.srms.domain.Schulform;
import com.srms.repository.SchulformRepository;
import com.srms.service.dto.SchulformDTO;
import com.srms.service.mapper.SchulformMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Schulform.
 */
@Service
@Transactional
public class SchulformServiceImpl implements SchulformService{

    private final Logger log = LoggerFactory.getLogger(SchulformServiceImpl.class);
    
    private final SchulformRepository schulformRepository;

    private final SchulformMapper schulformMapper;

    public SchulformServiceImpl(SchulformRepository schulformRepository, SchulformMapper schulformMapper) {
        this.schulformRepository = schulformRepository;
        this.schulformMapper = schulformMapper;
    }

    /**
     * Save a schulform.
     *
     * @param schulformDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SchulformDTO save(SchulformDTO schulformDTO) {
        log.debug("Request to save Schulform : {}", schulformDTO);
        Schulform schulform = schulformMapper.toEntity(schulformDTO);
        schulform = schulformRepository.save(schulform);
        SchulformDTO result = schulformMapper.toDto(schulform);
        return result;
    }

    /**
     *  Get all the schulforms.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SchulformDTO> findAll() {
        log.debug("Request to get all Schulforms");
        List<SchulformDTO> result = schulformRepository.findAll().stream()
            .map(schulformMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one schulform by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SchulformDTO findOne(Long id) {
        log.debug("Request to get Schulform : {}", id);
        Schulform schulform = schulformRepository.findOne(id);
        SchulformDTO schulformDTO = schulformMapper.toDto(schulform);
        return schulformDTO;
    }

    /**
     *  Delete the  schulform by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Schulform : {}", id);
        schulformRepository.delete(id);
    }
}
