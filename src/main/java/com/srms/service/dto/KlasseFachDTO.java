package com.srms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the KlasseFach entity.
 */
public class KlasseFachDTO implements Serializable {

    private Long id;

    private Long klasseId;

    private Long lehrerId;

    private Long fachId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKlasseId() {
        return klasseId;
    }

    public void setKlasseId(Long klasseId) {
        this.klasseId = klasseId;
    }

    public Long getLehrerId() {
        return lehrerId;
    }

    public void setLehrerId(Long lehrerId) {
        this.lehrerId = lehrerId;
    }

    public Long getFachId() {
        return fachId;
    }

    public void setFachId(Long fachId) {
        this.fachId = fachId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KlasseFachDTO klasseFachDTO = (KlasseFachDTO) o;
        if(klasseFachDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), klasseFachDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KlasseFachDTO{" +
            "id=" + getId() +
            "}";
    }
}
