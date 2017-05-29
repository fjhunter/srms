package com.srms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Lehrer entity.
 */
public class LehrerDTO implements Serializable {

    private Long id;

    @NotNull
    private String vorname;

    @NotNull
    private String namen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNamen() {
        return namen;
    }

    public void setNamen(String namen) {
        this.namen = namen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LehrerDTO lehrerDTO = (LehrerDTO) o;
        if(lehrerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lehrerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LehrerDTO{" +
            "id=" + getId() +
            ", vorname='" + getVorname() + "'" +
            ", namen='" + getNamen() + "'" +
            "}";
    }
}
