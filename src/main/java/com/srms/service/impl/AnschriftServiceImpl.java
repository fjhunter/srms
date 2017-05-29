package com.srms.service.impl;

import com.srms.service.AnschriftService;
import com.srms.domain.Anschrift;
import com.srms.repository.AnschriftRepository;
import com.srms.service.dto.AnschriftDTO;
import com.srms.service.mapper.AnschriftMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Anschrift.
 */
@Service
@Transactional
public class AnschriftServiceImpl implements AnschriftService{

    private final Logger log = LoggerFactory.getLogger(AnschriftServiceImpl.class);
    
    private final AnschriftRepository anschriftRepository;

    private final AnschriftMapper anschriftMapper;

    public AnschriftServiceImpl(AnschriftRepository anschriftRepository, AnschriftMapper anschriftMapper) {
        this.anschriftRepository = anschriftRepository;
        this.anschriftMapper = anschriftMapper;
    }

    /**
     * Save a anschrift.
     *
     * @param anschriftDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AnschriftDTO save(AnschriftDTO anschriftDTO) {
        log.debug("Request to save Anschrift : {}", anschriftDTO);
        Anschrift anschrift = anschriftMapper.toEntity(anschriftDTO);
        anschrift = anschriftRepository.save(anschrift);
        AnschriftDTO result = anschriftMapper.toDto(anschrift);
        return result;
    }

    /**
     *  Get all the anschrifts.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AnschriftDTO> findAll() {
        log.debug("Request to get all Anschrifts");
        List<AnschriftDTO> result = anschriftRepository.findAll().stream()
            .map(anschriftMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one anschrift by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AnschriftDTO findOne(Long id) {
        log.debug("Request to get Anschrift : {}", id);
        Anschrift anschrift = anschriftRepository.findOne(id);
        AnschriftDTO anschriftDTO = anschriftMapper.toDto(anschrift);
        return anschriftDTO;
    }

    /**
     *  Delete the  anschrift by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anschrift : {}", id);
        anschriftRepository.delete(id);
    }
}
