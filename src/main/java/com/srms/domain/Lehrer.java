package com.srms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lehrer.
 */
@Entity
@Table(name = "lehrer")
public class Lehrer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "vorname", nullable = false)
    private String vorname;

    @NotNull
    @Column(name = "namen", nullable = false)
    private String namen;

    @OneToMany(mappedBy = "lehrer")
    @JsonIgnore
    private Set<KlasseFach> unterrichtets = new HashSet<>();

    @OneToMany(mappedBy = "lehrer")
    @JsonIgnore
    private Set<Klasse> klassenlehrers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public Lehrer vorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNamen() {
        return namen;
    }

    public Lehrer namen(String namen) {
        this.namen = namen;
        return this;
    }

    public void setNamen(String namen) {
        this.namen = namen;
    }

    public Set<KlasseFach> getUnterrichtets() {
        return unterrichtets;
    }

    public Lehrer unterrichtets(Set<KlasseFach> klasseFaches) {
        this.unterrichtets = klasseFaches;
        return this;
    }

    public Lehrer addUnterrichtet(KlasseFach klasseFach) {
        this.unterrichtets.add(klasseFach);
        klasseFach.setLehrer(this);
        return this;
    }

    public Lehrer removeUnterrichtet(KlasseFach klasseFach) {
        this.unterrichtets.remove(klasseFach);
        klasseFach.setLehrer(null);
        return this;
    }

    public void setUnterrichtets(Set<KlasseFach> klasseFaches) {
        this.unterrichtets = klasseFaches;
    }

    public Set<Klasse> getKlassenlehrers() {
        return klassenlehrers;
    }

    public Lehrer klassenlehrers(Set<Klasse> klasses) {
        this.klassenlehrers = klasses;
        return this;
    }

    public Lehrer addKlassenlehrer(Klasse klasse) {
        this.klassenlehrers.add(klasse);
        klasse.setLehrer(this);
        return this;
    }

    public Lehrer removeKlassenlehrer(Klasse klasse) {
        this.klassenlehrers.remove(klasse);
        klasse.setLehrer(null);
        return this;
    }

    public void setKlassenlehrers(Set<Klasse> klasses) {
        this.klassenlehrers = klasses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lehrer lehrer = (Lehrer) o;
        if (lehrer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lehrer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lehrer{" +
            "id=" + getId() +
            ", vorname='" + getVorname() + "'" +
            ", namen='" + getNamen() + "'" +
            "}";
    }
}
