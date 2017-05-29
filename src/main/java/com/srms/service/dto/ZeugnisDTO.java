package com.srms.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.srms.domain.enumeration.Zeugnis_typ;

/**
 * A DTO for the Zeugnis entity.
 */
public class ZeugnisDTO implements Serializable {

    private Long id;

    private Integer sozialverhalten;

    private Integer arbeitsverhalten;

    @NotNull
    private ZonedDateTime datum;

    @NotNull
    private Zeugnis_typ zeugnistyp;

    private Long schuelerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSozialverhalten() {
        return sozialverhalten;
    }

    public void setSozialverhalten(Integer sozialverhalten) {
        this.sozialverhalten = sozialverhalten;
    }

    public Integer getArbeitsverhalten() {
        return arbeitsverhalten;
    }

    public void setArbeitsverhalten(Integer arbeitsverhalten) {
        this.arbeitsverhalten = arbeitsverhalten;
    }

    public ZonedDateTime getDatum() {
        return datum;
    }

    public void setDatum(ZonedDateTime datum) {
        this.datum = datum;
    }

    public Zeugnis_typ getZeugnistyp() {
        return zeugnistyp;
    }

    public void setZeugnistyp(Zeugnis_typ zeugnistyp) {
        this.zeugnistyp = zeugnistyp;
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

        ZeugnisDTO zeugnisDTO = (ZeugnisDTO) o;
        if(zeugnisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zeugnisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZeugnisDTO{" +
            "id=" + getId() +
            ", sozialverhalten='" + getSozialverhalten() + "'" +
            ", arbeitsverhalten='" + getArbeitsverhalten() + "'" +
            ", datum='" + getDatum() + "'" +
            ", zeugnistyp='" + getZeugnistyp() + "'" +
            "}";
    }
}
