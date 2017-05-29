package com.srms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Fehlzeiten.
 */
@Entity
@Table(name = "fehlzeiten")
public class Fehlzeiten implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "datum", nullable = false)
    private ZonedDateTime datum;

    @NotNull
    @Column(name = "dauer", nullable = false)
    private Integer dauer;

    @ManyToOne
    private Schueler schueler;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDatum() {
        return datum;
    }

    public Fehlzeiten datum(ZonedDateTime datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(ZonedDateTime datum) {
        this.datum = datum;
    }

    public Integer getDauer() {
        return dauer;
    }

    public Fehlzeiten dauer(Integer dauer) {
        this.dauer = dauer;
        return this;
    }

    public void setDauer(Integer dauer) {
        this.dauer = dauer;
    }

    public Schueler getSchueler() {
        return schueler;
    }

    public Fehlzeiten schueler(Schueler schueler) {
        this.schueler = schueler;
        return this;
    }

    public void setSchueler(Schueler schueler) {
        this.schueler = schueler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fehlzeiten fehlzeiten = (Fehlzeiten) o;
        if (fehlzeiten.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fehlzeiten.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fehlzeiten{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", dauer='" + getDauer() + "'" +
            "}";
    }
}
