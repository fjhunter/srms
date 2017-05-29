package com.srms.service.mapper;

import com.srms.domain.*;
import com.srms.service.dto.AnschriftDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Anschrift and its DTO AnschriftDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnschriftMapper extends EntityMapper <AnschriftDTO, Anschrift> {
    
    @Mapping(target = "anschrifts", ignore = true)
    Anschrift toEntity(AnschriftDTO anschriftDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Anschrift fromId(Long id) {
        if (id == null) {
            return null;
        }
        Anschrift anschrift = new Anschrift();
        anschrift.setId(id);
        return anschrift;
    }
}
