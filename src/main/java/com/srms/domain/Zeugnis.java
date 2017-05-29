package com.srms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.srms.domain.enumeration.Zeugnis_typ;

/**
 * A Zeugnis.
 */
@Entity
@Table(name = "zeugnis")
public class Zeugnis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sozialverhalten")
    private Integer sozialverhalten;

    @Column(name = "arbeitsverhalten")
    private Integer arbeitsverhalten;

    @NotNull
    @Column(name = "datum", nullable = false)
    private ZonedDateTime datum;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "zeugnistyp", nullable = false)
    private Zeugnis_typ zeugnistyp;

    @ManyToOne
    private Schueler schueler;

    @OneToMany(mappedBy = "zeugnis")
    @JsonIgnore
    private Set<ZeugnisFach> faches = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSozialverhalten() {
        return sozialverhalten;
    }

    public Zeugnis sozialverhalten(Integer sozialverhalten) {
        this.sozialverhalten = sozialverhalten;
        return this;
    }

    public void setSozialverhalten(Integer sozialverhalten) {
        this.sozialverhalten = sozialverhalten;
    }

    public Integer getArbeitsverhalten() {
        return arbeitsverhalten;
    }

    public Zeugnis arbeitsverhalten(Integer arbeitsverhalten) {
        this.arbeitsverhalten = arbeitsverhalten;
        return this;
    }

    public void setArbeitsverhalten(Integer arbeitsverhalten) {
        this.arbeitsverhalten = arbeitsverhalten;
    }

    public ZonedDateTime getDatum() {
        return datum;
    }

    public Zeugnis datum(ZonedDateTime datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(ZonedDateTime datum) {
        this.datum = datum;
    }

    public Zeugnis_typ getZeugnistyp() {
        return zeugnistyp;
    }

    public Zeugnis zeugnistyp(Zeugnis_typ zeugnistyp) {
        this.zeugnistyp = zeugnistyp;
        return this;
    }

    public void setZeugnistyp(Zeugnis_typ zeugnistyp) {
        this.zeugnistyp = zeugnistyp;
    }

    public Schueler getSchueler() {
        return schueler;
    }

    public Zeugnis schueler(Schueler schueler) {
        this.schueler = schueler;
        return this;
    }

    public void setSchueler(Schueler schueler) {
        this.schueler = schueler;
    }

    public Set<ZeugnisFach> getFaches() {
        return faches;
    }

    public Zeugnis faches(Set<ZeugnisFach> zeugnisFaches) {
        this.faches = zeugnisFaches;
        return this;
    }

    public Zeugnis addFach(ZeugnisFach zeugnisFach) {
        this.faches.add(zeugnisFach);
        zeugnisFach.setZeugnis(this);
        return this;
    }

    public Zeugnis removeFach(ZeugnisFach zeugnisFach) {
        this.faches.remove(zeugnisFach);
        zeugnisFach.setZeugnis(null);
        return this;
    }

    public void setFaches(Set<ZeugnisFach> zeugnisFaches) {
        this.faches = zeugnisFaches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Zeugnis zeugnis = (Zeugnis) o;
        if (zeugnis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zeugnis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Zeugnis{" +
            "id=" + getId() +
            ", sozialverhalten='" + getSozialverhalten() + "'" +
            ", arbeitsverhalten='" + getArbeitsverhalten() + "'" +
            ", datum='" + getDatum() + "'" +
            ", zeugnistyp='" + getZeugnistyp() + "'" +
            "}";
    }
}
