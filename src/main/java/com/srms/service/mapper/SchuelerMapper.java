package com.srms.service.mapper;

import com.srms.domain.*;
import com.srms.service.dto.SchuelerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Schueler and its DTO SchuelerDTO.
 */
@Mapper(componentModel = "spring", uses = {KlasseMapper.class, AnschriftMapper.class, })
public interface SchuelerMapper extends EntityMapper <SchuelerDTO, Schueler> {
    @Mapping(source = "klasse.id", target = "klasseId")
    @Mapping(source = "anschrift.id", target = "anschriftId")
    SchuelerDTO toDto(Schueler schueler); 
    @Mapping(target = "schuelers", ignore = true)
    @Mapping(target = "fehlzeitens", ignore = true)
    @Mapping(source = "klasseId", target = "klasse")
    @Mapping(source = "anschriftId", target = "anschrift")
    Schueler toEntity(SchuelerDTO schuelerDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Schueler fromId(Long id) {
        if (id == null) {
            return null;
        }
        Schueler schueler = new Schueler();
        schueler.setId(id);
        return schueler;
    }
}
