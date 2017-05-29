package com.srms.service.mapper;

import com.srms.domain.*;
import com.srms.service.dto.ZeugnisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Zeugnis and its DTO ZeugnisDTO.
 */
@Mapper(componentModel = "spring", uses = {SchuelerMapper.class, })
public interface ZeugnisMapper extends EntityMapper <ZeugnisDTO, Zeugnis> {
    @Mapping(source = "schueler.id", target = "schuelerId")
    ZeugnisDTO toDto(Zeugnis zeugnis); 
    @Mapping(source = "schuelerId", target = "schueler")
    @Mapping(target = "faches", ignore = true)
    Zeugnis toEntity(ZeugnisDTO zeugnisDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Zeugnis fromId(Long id) {
        if (id == null) {
            return null;
        }
        Zeugnis zeugnis = new Zeugnis();
        zeugnis.setId(id);
        return zeugnis;
    }
}
