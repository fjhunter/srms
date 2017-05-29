package com.srms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Schulform entity.
 */
public class SchulformDTO implements Serializable {

    private Long id;

    @NotNull
    private String bezeichnung;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchulformDTO schulformDTO = (SchulformDTO) o;
        if(schulformDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schulformDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchulformDTO{" +
            "id=" + getId() +
            ", bezeichnung='" + getBezeichnung() + "'" +
            "}";
    }
}
