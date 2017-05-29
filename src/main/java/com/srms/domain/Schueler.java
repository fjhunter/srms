package com.srms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.srms.domain.enumeration.Schulform;

/**
 * A Schueler.
 */
@Entity
@Table(name = "schueler")
public class Schueler implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "vorname", nullable = false)
    private String vorname;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "schulform", nullable = false)
    private Schulform schulform;

    @OneToMany(mappedBy = "schueler")
    @JsonIgnore
    private Set<Zeugnis> schuelers = new HashSet<>();

    @OneToMany(mappedBy = "schueler")
    @JsonIgnore
    private Set<Fehlzeiten> fehlzeitens = new HashSet<>();

    @ManyToOne
    private Klasse klasse;

    @ManyToOne
    private Anschrift anschrift;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Schueler name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public Schueler vorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Schulform getSchulform() {
        return schulform;
    }

    public Schueler schulform(Schulform schulform) {
        this.schulform = schulform;
        return this;
    }

    public void setSchulform(Schulform schulform) {
        this.schulform = schulform;
    }

    public Set<Zeugnis> getSchuelers() {
        return schuelers;
    }

    public Schueler schuelers(Set<Zeugnis> zeugnis) {
        this.schuelers = zeugnis;
        return this;
    }

    public Schueler addSchueler(Zeugnis zeugnis) {
        this.schuelers.add(zeugnis);
        zeugnis.setSchueler(this);
        return this;
    }

    public Schueler removeSchueler(Zeugnis zeugnis) {
        this.schuelers.remove(zeugnis);
        zeugnis.setSchueler(null);
        return this;
    }

    public void setSchuelers(Set<Zeugnis> zeugnis) {
        this.schuelers = zeugnis;
    }

    public Set<Fehlzeiten> getFehlzeitens() {
        return fehlzeitens;
    }

    public Schueler fehlzeitens(Set<Fehlzeiten> fehlzeitens) {
        this.fehlzeitens = fehlzeitens;
        return this;
    }

    public Schueler addFehlzeiten(Fehlzeiten fehlzeiten) {
        this.fehlzeitens.add(fehlzeiten);
        fehlzeiten.setSchueler(this);
        return this;
    }

    public Schueler removeFehlzeiten(Fehlzeiten fehlzeiten) {
        this.fehlzeitens.remove(fehlzeiten);
        fehlzeiten.setSchueler(null);
        return this;
    }

    public void setFehlzeitens(Set<Fehlzeiten> fehlzeitens) {
        this.fehlzeitens = fehlzeitens;
    }

    public Klasse getKlasse() {
        return klasse;
    }

    public Schueler klasse(Klasse klasse) {
        this.klasse = klasse;
        return this;
    }

    public void setKlasse(Klasse klasse) {
        this.klasse = klasse;
    }

    public Anschrift getAnschrift() {
        return anschrift;
    }

    public Schueler anschrift(Anschrift anschrift) {
        this.anschrift = anschrift;
        return this;
    }

    public void setAnschrift(Anschrift anschrift) {
        this.anschrift = anschrift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schueler schueler = (Schueler) o;
        if (schueler.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schueler.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Schueler{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vorname='" + getVorname() + "'" +
            ", schulform='" + getSchulform() + "'" +
            "}";
    }
}
