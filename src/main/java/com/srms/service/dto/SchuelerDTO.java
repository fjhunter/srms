package com.srms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.srms.domain.enumeration.Schulform;

/**
 * A DTO for the Schueler entity.
 */
public class SchuelerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String vorname;

    @NotNull
    private Schulform schulform;

    private Long klasseId;

    private Long anschriftId;

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

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Schulform getSchulform() {
        return schulform;
    }

    public void setSchulform(Schulform schulform) {
        this.schulform = schulform;
    }

    public Long getKlasseId() {
        return klasseId;
    }

    public void setKlasseId(Long klasseId) {
        this.klasseId = klasseId;
    }

    public Long getAnschriftId() {
        return anschriftId;
    }

    public void setAnschriftId(Long anschriftId) {
        this.anschriftId = anschriftId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchuelerDTO schuelerDTO = (SchuelerDTO) o;
        if(schuelerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schuelerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchuelerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vorname='" + getVorname() + "'" +
            ", schulform='" + getSchulform() + "'" +
            "}";
    }
}
