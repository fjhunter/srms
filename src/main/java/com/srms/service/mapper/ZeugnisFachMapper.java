package com.srms.service.mapper;

import com.srms.domain.*;
import com.srms.service.dto.ZeugnisFachDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZeugnisFach and its DTO ZeugnisFachDTO.
 */
@Mapper(componentModel = "spring", uses = {ZeugnisMapper.class, FachMapper.class, })
public interface ZeugnisFachMapper extends EntityMapper <ZeugnisFachDTO, ZeugnisFach> {
    @Mapping(source = "zeugnis.id", target = "zeugnisId")
    @Mapping(source = "fach.id", target = "fachId")
    ZeugnisFachDTO toDto(ZeugnisFach zeugnisFach); 
    @Mapping(source = "zeugnisId", target = "zeugnis")
    @Mapping(source = "fachId", target = "fach")
    ZeugnisFach toEntity(ZeugnisFachDTO zeugnisFachDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default ZeugnisFach fromId(Long id) {
        if (id == null) {
            return null;
        }
        ZeugnisFach zeugnisFach = new ZeugnisFach();
        zeugnisFach.setId(id);
        return zeugnisFach;
    }
}
