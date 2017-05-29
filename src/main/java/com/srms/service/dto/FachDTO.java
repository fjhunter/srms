package com.srms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Fach entity.
 */
public class FachDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FachDTO fachDTO = (FachDTO) o;
        if(fachDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fachDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FachDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
