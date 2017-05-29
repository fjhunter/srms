package com.srms.service.impl;

import com.srms.service.FehlzeitenService;
import com.srms.domain.Fehlzeiten;
import com.srms.repository.FehlzeitenRepository;
import com.srms.service.dto.FehlzeitenDTO;
import com.srms.service.mapper.FehlzeitenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Fehlzeiten.
 */
@Service
@Transactional
public class FehlzeitenServiceImpl implements FehlzeitenService{

    private final Logger log = LoggerFactory.getLogger(FehlzeitenServiceImpl.class);
    
    private final FehlzeitenRepository fehlzeitenRepository;

    private final FehlzeitenMapper fehlzeitenMapper;

    public FehlzeitenServiceImpl(FehlzeitenRepository fehlzeitenRepository, FehlzeitenMapper fehlzeitenMapper) {
        this.fehlzeitenRepository = fehlzeitenRepository;
        this.fehlzeitenMapper = fehlzeitenMapper;
    }

    /**
     * Save a fehlzeiten.
     *
     * @param fehlzeitenDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FehlzeitenDTO save(FehlzeitenDTO fehlzeitenDTO) {
        log.debug("Request to save Fehlzeiten : {}", fehlzeitenDTO);
        Fehlzeiten fehlzeiten = fehlzeitenMapper.toEntity(fehlzeitenDTO);
        fehlzeiten = fehlzeitenRepository.save(fehlzeiten);
        FehlzeitenDTO result = fehlzeitenMapper.toDto(fehlzeiten);
        return result;
    }

    /**
     *  Get all the fehlzeitens.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FehlzeitenDTO> findAll() {
        log.debug("Request to get all Fehlzeitens");
        List<FehlzeitenDTO> result = fehlzeitenRepository.findAll().stream()
            .map(fehlzeitenMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one fehlzeiten by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FehlzeitenDTO findOne(Long id) {
        log.debug("Request to get Fehlzeiten : {}", id);
        Fehlzeiten fehlzeiten = fehlzeitenRepository.findOne(id);
        FehlzeitenDTO fehlzeitenDTO = fehlzeitenMapper.toDto(fehlzeiten);
        return fehlzeitenDTO;
    }

    /**
     *  Delete the  fehlzeiten by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fehlzeiten : {}", id);
        fehlzeitenRepository.delete(id);
    }
}
