package com.srms.service.mapper;

import com.srms.domain.*;
import com.srms.service.dto.KlasseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Klasse and its DTO KlasseDTO.
 */
@Mapper(componentModel = "spring", uses = {LehrerMapper.class, })
public interface KlasseMapper extends EntityMapper <KlasseDTO, Klasse> {
    @Mapping(source = "lehrer.id", target = "lehrerId")
    KlasseDTO toDto(Klasse klasse); 
    @Mapping(target = "klasseFaches", ignore = true)
    @Mapping(target = "klasses", ignore = true)
    @Mapping(source = "lehrerId", target = "lehrer")
    Klasse toEntity(KlasseDTO klasseDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Klasse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Klasse klasse = new Klasse();
        klasse.setId(id);
        return klasse;
    }
}
