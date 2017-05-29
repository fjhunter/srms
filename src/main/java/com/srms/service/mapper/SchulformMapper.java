package com.srms.service.mapper;

import com.srms.domain.*;
import com.srms.service.dto.SchulformDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Schulform and its DTO SchulformDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchulformMapper extends EntityMapper <SchulformDTO, Schulform> {
    
    
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Schulform fromId(Long id) {
        if (id == null) {
            return null;
        }
        Schulform schulform = new Schulform();
        schulform.setId(id);
        return schulform;
    }
}
