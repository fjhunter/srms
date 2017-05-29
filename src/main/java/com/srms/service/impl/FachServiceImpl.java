package com.srms.service.impl;

import com.srms.service.FachService;
import com.srms.domain.Fach;
import com.srms.repository.FachRepository;
import com.srms.service.dto.FachDTO;
import com.srms.service.mapper.FachMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Fach.
 */
@Service
@Transactional
public class FachServiceImpl implements FachService{

    private final Logger log = LoggerFactory.getLogger(FachServiceImpl.class);
    
    private final FachRepository fachRepository;

    private final FachMapper fachMapper;

    public FachServiceImpl(FachRepository fachRepository, FachMapper fachMapper) {
        this.fachRepository = fachRepository;
        this.fachMapper = fachMapper;
    }

    /**
     * Save a fach.
     *
     * @param fachDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FachDTO save(FachDTO fachDTO) {
        log.debug("Request to save Fach : {}", fachDTO);
        Fach fach = fachMapper.toEntity(fachDTO);
        fach = fachRepository.save(fach);
        FachDTO result = fachMapper.toDto(fach);
        return result;
    }

    /**
     *  Get all the faches.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FachDTO> findAll() {
        log.debug("Request to get all Faches");
        List<FachDTO> result = fachRepository.findAll().stream()
            .map(fachMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one fach by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FachDTO findOne(Long id) {
        log.debug("Request to get Fach : {}", id);
        Fach fach = fachRepository.findOne(id);
        FachDTO fachDTO = fachMapper.toDto(fach);
        return fachDTO;
    }

    /**
     *  Delete the  fach by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fach : {}", id);
        fachRepository.delete(id);
    }
}
