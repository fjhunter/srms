package com.srms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Anschrift entity.
 */
public class AnschriftDTO implements Serializable {

    private Long id;

    @NotNull
    private String ort;

    @NotNull
    private Integer postleitzahl;

    @NotNull
    private String strassenname;

    @NotNull
    private Integer hausnummer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Integer getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(Integer postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getStrassenname() {
        return strassenname;
    }

    public void setStrassenname(String strassenname) {
        this.strassenname = strassenname;
    }

    public Integer getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(Integer hausnummer) {
        this.hausnummer = hausnummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnschriftDTO anschriftDTO = (AnschriftDTO) o;
        if(anschriftDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anschriftDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnschriftDTO{" +
            "id=" + getId() +
            ", ort='" + getOrt() + "'" +
            ", postleitzahl='" + getPostleitzahl() + "'" +
            ", strassenname='" + getStrassenname() + "'" +
            ", hausnummer='" + getHausnummer() + "'" +
            "}";
    }
}
