package com.srms.service.impl;

import com.srms.service.SchuelerService;
import com.srms.domain.Schueler;
import com.srms.repository.SchuelerRepository;
import com.srms.service.dto.SchuelerDTO;
import com.srms.service.mapper.SchuelerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Schueler.
 */
@Service
@Transactional
public class SchuelerServiceImpl implements SchuelerService{

    private final Logger log = LoggerFactory.getLogger(SchuelerServiceImpl.class);
    
    private final SchuelerRepository schuelerRepository;

    private final SchuelerMapper schuelerMapper;

    public SchuelerServiceImpl(SchuelerRepository schuelerRepository, SchuelerMapper schuelerMapper) {
        this.schuelerRepository = schuelerRepository;
        this.schuelerMapper = schuelerMapper;
    }

    /**
     * Save a schueler.
     *
     * @param schuelerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SchuelerDTO save(SchuelerDTO schuelerDTO) {
        log.debug("Request to save Schueler : {}", schuelerDTO);
        Schueler schueler = schuelerMapper.toEntity(schuelerDTO);
        schueler = schuelerRepository.save(schueler);
        SchuelerDTO result = schuelerMapper.toDto(schueler);
        return result;
    }

    /**
     *  Get all the schuelers.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SchuelerDTO> findAll() {
        log.debug("Request to get all Schuelers");
        List<SchuelerDTO> result = schuelerRepository.findAll().stream()
            .map(schuelerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one schueler by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SchuelerDTO findOne(Long id) {
        log.debug("Request to get Schueler : {}", id);
        Schueler schueler = schuelerRepository.findOne(id);
        SchuelerDTO schuelerDTO = schuelerMapper.toDto(schueler);
        return schuelerDTO;
    }

    /**
     *  Delete the  schueler by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Schueler : {}", id);
        schuelerRepository.delete(id);
    }
}
