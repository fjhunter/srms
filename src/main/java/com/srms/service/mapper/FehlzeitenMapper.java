package com.srms.service.mapper;

import com.srms.domain.*;
import com.srms.service.dto.FehlzeitenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fehlzeiten and its DTO FehlzeitenDTO.
 */
@Mapper(componentModel = "spring", uses = {SchuelerMapper.class, })
public interface FehlzeitenMapper extends EntityMapper <FehlzeitenDTO, Fehlzeiten> {
    @Mapping(source = "schueler.id", target = "schuelerId")
    FehlzeitenDTO toDto(Fehlzeiten fehlzeiten); 
    @Mapping(source = "schuelerId", target = "schueler")
    Fehlzeiten toEntity(FehlzeitenDTO fehlzeitenDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Fehlzeiten fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fehlzeiten fehlzeiten = new Fehlzeiten();
        fehlzeiten.setId(id);
        return fehlzeiten;
    }
}
