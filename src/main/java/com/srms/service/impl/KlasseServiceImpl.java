package com.srms.service.impl;

import com.srms.service.KlasseService;
import com.srms.domain.Klasse;
import com.srms.repository.KlasseRepository;
import com.srms.service.dto.KlasseDTO;
import com.srms.service.mapper.KlasseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Klasse.
 */
@Service
@Transactional
public class KlasseServiceImpl implements KlasseService{

    private final Logger log = LoggerFactory.getLogger(KlasseServiceImpl.class);
    
    private final KlasseRepository klasseRepository;

    private final KlasseMapper klasseMapper;

    public KlasseServiceImpl(KlasseRepository klasseRepository, KlasseMapper klasseMapper) {
        this.klasseRepository = klasseRepository;
        this.klasseMapper = klasseMapper;
    }

    /**
     * Save a klasse.
     *
     * @param klasseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KlasseDTO save(KlasseDTO klasseDTO) {
        log.debug("Request to save Klasse : {}", klasseDTO);
        Klasse klasse = klasseMapper.toEntity(klasseDTO);
        klasse = klasseRepository.save(klasse);
        KlasseDTO result = klasseMapper.toDto(klasse);
        return result;
    }

    /**
     *  Get all the klasses.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KlasseDTO> findAll() {
        log.debug("Request to get all Klasses");
        List<KlasseDTO> result = klasseRepository.findAll().stream()
            .map(klasseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one klasse by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KlasseDTO findOne(Long id) {
        log.debug("Request to get Klasse : {}", id);
        Klasse klasse = klasseRepository.findOne(id);
        KlasseDTO klasseDTO = klasseMapper.toDto(klasse);
        return klasseDTO;
    }

    /**
     *  Delete the  klasse by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Klasse : {}", id);
        klasseRepository.delete(id);
    }
}
