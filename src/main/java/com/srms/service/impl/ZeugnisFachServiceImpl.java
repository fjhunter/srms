package com.srms.service.impl;

import com.srms.service.ZeugnisFachService;
import com.srms.domain.ZeugnisFach;
import com.srms.repository.ZeugnisFachRepository;
import com.srms.service.dto.ZeugnisFachDTO;
import com.srms.service.mapper.ZeugnisFachMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ZeugnisFach.
 */
@Service
@Transactional
public class ZeugnisFachServiceImpl implements ZeugnisFachService{

    private final Logger log = LoggerFactory.getLogger(ZeugnisFachServiceImpl.class);
    
    private final ZeugnisFachRepository zeugnisFachRepository;

    private final ZeugnisFachMapper zeugnisFachMapper;

    public ZeugnisFachServiceImpl(ZeugnisFachRepository zeugnisFachRepository, ZeugnisFachMapper zeugnisFachMapper) {
        this.zeugnisFachRepository = zeugnisFachRepository;
        this.zeugnisFachMapper = zeugnisFachMapper;
    }

    /**
     * Save a zeugnisFach.
     *
     * @param zeugnisFachDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ZeugnisFachDTO save(ZeugnisFachDTO zeugnisFachDTO) {
        log.debug("Request to save ZeugnisFach : {}", zeugnisFachDTO);
        ZeugnisFach zeugnisFach = zeugnisFachMapper.toEntity(zeugnisFachDTO);
        zeugnisFach = zeugnisFachRepository.save(zeugnisFach);
        ZeugnisFachDTO result = zeugnisFachMapper.toDto(zeugnisFach);
        return result;
    }

    /**
     *  Get all the zeugnisFaches.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ZeugnisFachDTO> findAll() {
        log.debug("Request to get all ZeugnisFaches");
        List<ZeugnisFachDTO> result = zeugnisFachRepository.findAll().stream()
            .map(zeugnisFachMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one zeugnisFach by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ZeugnisFachDTO findOne(Long id) {
        log.debug("Request to get ZeugnisFach : {}", id);
        ZeugnisFach zeugnisFach = zeugnisFachRepository.findOne(id);
        ZeugnisFachDTO zeugnisFachDTO = zeugnisFachMapper.toDto(zeugnisFach);
        return zeugnisFachDTO;
    }

    /**
     *  Delete the  zeugnisFach by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ZeugnisFach : {}", id);
        zeugnisFachRepository.delete(id);
    }
}
