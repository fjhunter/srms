package com.srms.service.impl;

import com.srms.domain.KlasseFach;
import com.srms.domain.Zeugnis;
import com.srms.domain.ZeugnisFach;
import com.srms.domain.enumeration.Zeugnis_typ;
import com.srms.repository.*;
import com.srms.service.ZeugnisFachService;
import com.srms.service.ZeugnisService;
import com.srms.service.dto.ZeugnisDTO;
import com.srms.service.dto.ZeugnisFachDTO;
import com.srms.service.mapper.ZeugnisMapper;
import com.srms.web.rest.RealZeugnis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing Zeugnis.
 */
@Service
@Transactional
public class RealZeugnisService {

    private final Logger log = LoggerFactory.getLogger(RealZeugnisService.class);

    private final ZeugnisMapper zeugnisMapper;

    private final ZeugnisRepository zeugnisRepository;

    private final KlasseFachRepository klasseFachRepository;

    private final FachRepository fachRepository;

    private final KlasseRepository klasseRepository;

    private final SchuelerRepository schuelerRepository;

    private final ZeugnisService zeugnisService;

    private final ZeugnisFachService zeugnisFachService;

    private final ZeugnisFachRepository zeugnisFachRepository;

    public RealZeugnisService(ZeugnisRepository zeugnisRepository, ZeugnisMapper zeugnisMapper, ZeugnisRepository zeugnisRepository1, KlasseFachRepository klasseFachRepository, FachRepository fachRepository, KlasseRepository klasseRepository, SchuelerRepository schuelerRepository, ZeugnisService zeugnisService, ZeugnisFachService zeugnisFachService, ZeugnisFachRepository zeugnisFachRepository) {
        this.zeugnisFachRepository = zeugnisFachRepository;
        this.zeugnisFachService = zeugnisFachService;
        this.zeugnisService = zeugnisService;
        this.zeugnisRepository = zeugnisRepository;
        this.zeugnisMapper = zeugnisMapper;
        this.klasseFachRepository = klasseFachRepository;
        this.fachRepository = fachRepository;
        this.klasseRepository = klasseRepository;
        this.schuelerRepository = schuelerRepository;
    }


    public List<RealZeugnis> getRealZeugnise(Zeugnis_typ zeugnisTyp, Long LehrerId, ZonedDateTime date) {
        List<RealZeugnis> realZeugnise = new ArrayList<>();
        List<KlasseFach> klasseFachList = klasseFachRepository.findByLehrerId(LehrerId);
        klasseFachList.stream().forEach(klasseFach -> {
            schuelerRepository.findByKlasse_Id(klasseFach.getKlasse().getId()).stream().forEach(schueler -> {
                RealZeugnis realZeugnis = new RealZeugnis();
                realZeugnis.setFach(klasseFach.getFach());
                realZeugnis.setKlasse(klasseFach.getKlasse());
                realZeugnis.setSchueler(schueler);
                Zeugnis zeugnis = getOrCreateZeugnis(schueler.getId(),zeugnisTyp,date);
                realZeugnis.setZeugnis(zeugnis);
                ZeugnisFach zeugnisFach = getOrCreateZeugnisFach(zeugnis.getId(), klasseFach.getFach().getId());
                realZeugnis.setNote(zeugnisFach);
                realZeugnise.add(realZeugnis);
            });
        });
        return realZeugnise;
    }

    private ZeugnisFach getOrCreateZeugnisFach(Long zeugnisId, Long fachId) {
        ZeugnisFach zeugnisFach = zeugnisFachRepository.findByZeugnisIdAndFachId(zeugnisId,fachId);
        if(zeugnisFach == null) {
            ZeugnisFachDTO zeugnisFachDTO = new ZeugnisFachDTO();
            zeugnisFachDTO.setFachId(fachId);
            zeugnisFachDTO.setZeugnisId(zeugnisId);
            zeugnisFachService.save(zeugnisFachDTO);
            zeugnisFach = zeugnisFachRepository.findByZeugnisIdAndFachId(zeugnisId,fachId);
        }
        return zeugnisFach;
    }

    private Zeugnis getOrCreateZeugnis(Long schulerId, Zeugnis_typ zeugnisTyp, ZonedDateTime date) {
        Zeugnis zeugnis = zeugnisRepository.findBySchuelerIdAndAndDatumAndAndZeugnistyp(schulerId,date,zeugnisTyp);
        if (zeugnis == null) {
            ZeugnisDTO zeugnisDTO = new ZeugnisDTO();
            zeugnisDTO.setZeugnistyp(zeugnisTyp);
            zeugnisDTO.setDatum(date);
            zeugnisDTO.setSchuelerId(schulerId);
            zeugnisService.save(zeugnisDTO);
            zeugnis = zeugnisRepository.findBySchuelerIdAndAndDatumAndAndZeugnistyp(schulerId,date,zeugnisTyp);
        }
        return zeugnis;
    }
}
