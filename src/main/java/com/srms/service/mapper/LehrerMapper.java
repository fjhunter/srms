package com.srms.service.mapper;

import com.srms.domain.*;
import com.srms.service.dto.LehrerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lehrer and its DTO LehrerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LehrerMapper extends EntityMapper <LehrerDTO, Lehrer> {
    
    @Mapping(target = "unterrichtets", ignore = true)
    @Mapping(target = "klassenlehrers", ignore = true)
    Lehrer toEntity(LehrerDTO lehrerDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Lehrer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lehrer lehrer = new Lehrer();
        lehrer.setId(id);
        return lehrer;
    }
}
