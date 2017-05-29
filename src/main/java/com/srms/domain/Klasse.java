package com.srms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Klasse.
 */
@Entity
@Table(name = "klasse")
public class Klasse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "klasse")
    @JsonIgnore
    private Set<KlasseFach> klasseFaches = new HashSet<>();

    @OneToMany(mappedBy = "klasse")
    @JsonIgnore
    private Set<Schueler> klasses = new HashSet<>();

    @ManyToOne
    private Lehrer lehrer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Klasse name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<KlasseFach> getKlasseFaches() {
        return klasseFaches;
    }

    public Klasse klasseFaches(Set<KlasseFach> klasseFaches) {
        this.klasseFaches = klasseFaches;
        return this;
    }

    public Klasse addKlasseFach(KlasseFach klasseFach) {
        this.klasseFaches.add(klasseFach);
        klasseFach.setKlasse(this);
        return this;
    }

    public Klasse removeKlasseFach(KlasseFach klasseFach) {
        this.klasseFaches.remove(klasseFach);
        klasseFach.setKlasse(null);
        return this;
    }

    public void setKlasseFaches(Set<KlasseFach> klasseFaches) {
        this.klasseFaches = klasseFaches;
    }

    public Set<Schueler> getKlasses() {
        return klasses;
    }

    public Klasse klasses(Set<Schueler> schuelers) {
        this.klasses = schuelers;
        return this;
    }

    public Klasse addKlasse(Schueler schueler) {
        this.klasses.add(schueler);
        schueler.setKlasse(this);
        return this;
    }

    public Klasse removeKlasse(Schueler schueler) {
        this.klasses.remove(schueler);
        schueler.setKlasse(null);
        return this;
    }

    public void setKlasses(Set<Schueler> schuelers) {
        this.klasses = schuelers;
    }

    public Lehrer getLehrer() {
        return lehrer;
    }

    public Klasse lehrer(Lehrer lehrer) {
        this.lehrer = lehrer;
        return this;
    }

    public void setLehrer(Lehrer lehrer) {
        this.lehrer = lehrer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Klasse klasse = (Klasse) o;
        if (klasse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), klasse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Klasse{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
