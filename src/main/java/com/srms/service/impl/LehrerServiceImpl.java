package com.srms.service.impl;

import com.srms.service.LehrerService;
import com.srms.domain.Lehrer;
import com.srms.repository.LehrerRepository;
import com.srms.service.dto.LehrerDTO;
import com.srms.service.mapper.LehrerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Lehrer.
 */
@Service
@Transactional
public class LehrerServiceImpl implements LehrerService{

    private final Logger log = LoggerFactory.getLogger(LehrerServiceImpl.class);
    
    private final LehrerRepository lehrerRepository;

    private final LehrerMapper lehrerMapper;

    public LehrerServiceImpl(LehrerRepository lehrerRepository, LehrerMapper lehrerMapper) {
        this.lehrerRepository = lehrerRepository;
        this.lehrerMapper = lehrerMapper;
    }

    /**
     * Save a lehrer.
     *
     * @param lehrerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LehrerDTO save(LehrerDTO lehrerDTO) {
        log.debug("Request to save Lehrer : {}", lehrerDTO);
        Lehrer lehrer = lehrerMapper.toEntity(lehrerDTO);
        lehrer = lehrerRepository.save(lehrer);
        LehrerDTO result = lehrerMapper.toDto(lehrer);
        return result;
    }

    /**
     *  Get all the lehrers.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LehrerDTO> findAll() {
        log.debug("Request to get all Lehrers");
        List<LehrerDTO> result = lehrerRepository.findAll().stream()
            .map(lehrerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one lehrer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LehrerDTO findOne(Long id) {
        log.debug("Request to get Lehrer : {}", id);
        Lehrer lehrer = lehrerRepository.findOne(id);
        LehrerDTO lehrerDTO = lehrerMapper.toDto(lehrer);
        return lehrerDTO;
    }

    /**
     *  Delete the  lehrer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lehrer : {}", id);
        lehrerRepository.delete(id);
    }
}
