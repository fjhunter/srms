package com.srms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Anschrift.
 */
@Entity
@Table(name = "anschrift")
public class Anschrift implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ort", nullable = false)
    private String ort;

    @NotNull
    @Column(name = "postleitzahl", nullable = false)
    private Integer postleitzahl;

    @NotNull
    @Column(name = "strassenname", nullable = false)
    private String strassenname;

    @NotNull
    @Column(name = "hausnummer", nullable = false)
    private Integer hausnummer;

    @OneToMany(mappedBy = "anschrift")
    @JsonIgnore
    private Set<Schueler> anschrifts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrt() {
        return ort;
    }

    public Anschrift ort(String ort) {
        this.ort = ort;
        return this;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Integer getPostleitzahl() {
        return postleitzahl;
    }

    public Anschrift postleitzahl(Integer postleitzahl) {
        this.postleitzahl = postleitzahl;
        return this;
    }

    public void setPostleitzahl(Integer postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getStrassenname() {
        return strassenname;
    }

    public Anschrift strassenname(String strassenname) {
        this.strassenname = strassenname;
        return this;
    }

    public void setStrassenname(String strassenname) {
        this.strassenname = strassenname;
    }

    public Integer getHausnummer() {
        return hausnummer;
    }

    public Anschrift hausnummer(Integer hausnummer) {
        this.hausnummer = hausnummer;
        return this;
    }

    public void setHausnummer(Integer hausnummer) {
        this.hausnummer = hausnummer;
    }

    public Set<Schueler> getAnschrifts() {
        return anschrifts;
    }

    public Anschrift anschrifts(Set<Schueler> schuelers) {
        this.anschrifts = schuelers;
        return this;
    }

    public Anschrift addAnschrift(Schueler schueler) {
        this.anschrifts.add(schueler);
        schueler.setAnschrift(this);
        return this;
    }

    public Anschrift removeAnschrift(Schueler schueler) {
        this.anschrifts.remove(schueler);
        schueler.setAnschrift(null);
        return this;
    }

    public void setAnschrifts(Set<Schueler> schuelers) {
        this.anschrifts = schuelers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Anschrift anschrift = (Anschrift) o;
        if (anschrift.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anschrift.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Anschrift{" +
            "id=" + getId() +
            ", ort='" + getOrt() + "'" +
            ", postleitzahl='" + getPostleitzahl() + "'" +
            ", strassenname='" + getStrassenname() + "'" +
            ", hausnummer='" + getHausnummer() + "'" +
            "}";
    }
}
