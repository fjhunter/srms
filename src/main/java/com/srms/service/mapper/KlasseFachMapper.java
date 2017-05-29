package com.srms.service.mapper;

import com.srms.domain.*;
import com.srms.service.dto.KlasseFachDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity KlasseFach and its DTO KlasseFachDTO.
 */
@Mapper(componentModel = "spring", uses = {KlasseMapper.class, LehrerMapper.class, FachMapper.class, })
public interface KlasseFachMapper extends EntityMapper <KlasseFachDTO, KlasseFach> {
    @Mapping(source = "klasse.id", target = "klasseId")
    @Mapping(source = "lehrer.id", target = "lehrerId")
    @Mapping(source = "fach.id", target = "fachId")
    KlasseFachDTO toDto(KlasseFach klasseFach); 
    @Mapping(source = "klasseId", target = "klasse")
    @Mapping(source = "lehrerId", target = "lehrer")
    @Mapping(source = "fachId", target = "fach")
    KlasseFach toEntity(KlasseFachDTO klasseFachDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default KlasseFach fromId(Long id) {
        if (id == null) {
            return null;
        }
        KlasseFach klasseFach = new KlasseFach();
        klasseFach.setId(id);
        return klasseFach;
    }
}
