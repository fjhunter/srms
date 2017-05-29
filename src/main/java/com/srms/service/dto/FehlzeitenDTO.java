package com.srms.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Fehlzeiten entity.
 */
public class FehlzeitenDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime datum;

    @NotNull
    private Integer dauer;

    private Long schuelerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDatum() {
        return datum;
    }

    public void setDatum(ZonedDateTime datum) {
        this.datum = datum;
    }

    public Integer getDauer() {
        return dauer;
    }

    public void setDauer(Integer dauer) {
        this.dauer = dauer;
    }

    public Long getSchuelerId() {
        return schuelerId;
    }

    public void setSchuelerId(Long schuelerId) {
        this.schuelerId = schuelerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FehlzeitenDTO fehlzeitenDTO = (FehlzeitenDTO) o;
        if(fehlzeitenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fehlzeitenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FehlzeitenDTO{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", dauer='" + getDauer() + "'" +
            "}";
    }
}
