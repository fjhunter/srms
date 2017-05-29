package com.srms.service.impl;

import com.srms.service.ZeugnisService;
import com.srms.domain.Zeugnis;
import com.srms.repository.ZeugnisRepository;
import com.srms.service.dto.ZeugnisDTO;
import com.srms.service.mapper.ZeugnisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Zeugnis.
 */
@Service
@Transactional
public class ZeugnisServiceImpl implements ZeugnisService{

    private final Logger log = LoggerFactory.getLogger(ZeugnisServiceImpl.class);
    
    private final ZeugnisRepository zeugnisRepository;

    private final ZeugnisMapper zeugnisMapper;

    public ZeugnisServiceImpl(ZeugnisRepository zeugnisRepository, ZeugnisMapper zeugnisMapper) {
        this.zeugnisRepository = zeugnisRepository;
        this.zeugnisMapper = zeugnisMapper;
    }

    /**
     * Save a zeugnis.
     *
     * @param zeugnisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ZeugnisDTO save(ZeugnisDTO zeugnisDTO) {
        log.debug("Request to save Zeugnis : {}", zeugnisDTO);
        Zeugnis zeugnis = zeugnisMapper.toEntity(zeugnisDTO);
        zeugnis = zeugnisRepository.save(zeugnis);
        ZeugnisDTO result = zeugnisMapper.toDto(zeugnis);
        return result;
    }

    /**
     *  Get all the zeugnis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ZeugnisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Zeugnis");
        Page<Zeugnis> result = zeugnisRepository.findAll(pageable);
        return result.map(zeugnis -> zeugnisMapper.toDto(zeugnis));
    }

    /**
     *  Get one zeugnis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ZeugnisDTO findOne(Long id) {
        log.debug("Request to get Zeugnis : {}", id);
        Zeugnis zeugnis = zeugnisRepository.findOne(id);
        ZeugnisDTO zeugnisDTO = zeugnisMapper.toDto(zeugnis);
        return zeugnisDTO;
    }

    /**
     *  Delete the  zeugnis by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Zeugnis : {}", id);
        zeugnisRepository.delete(id);
    }
}
