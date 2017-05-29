package com.srms.service.mapper;

import com.srms.domain.*;
import com.srms.service.dto.FachDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fach and its DTO FachDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FachMapper extends EntityMapper <FachDTO, Fach> {
    
    @Mapping(target = "fufus", ignore = true)
    @Mapping(target = "faches", ignore = true)
    Fach toEntity(FachDTO fachDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Fach fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fach fach = new Fach();
        fach.setId(id);
        return fach;
    }
}
