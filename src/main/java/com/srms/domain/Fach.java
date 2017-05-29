package com.srms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fach.
 */
@Entity
@Table(name = "fach")
public class Fach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "fach")
    @JsonIgnore
    private Set<KlasseFach> fufus = new HashSet<>();

    @OneToMany(mappedBy = "fach")
    @JsonIgnore
    private Set<ZeugnisFach> faches = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Fach name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<KlasseFach> getFufus() {
        return fufus;
    }

    public Fach fufus(Set<KlasseFach> klasseFaches) {
        this.fufus = klasseFaches;
        return this;
    }

    public Fach addFufu(KlasseFach klasseFach) {
        this.fufus.add(klasseFach);
        klasseFach.setFach(this);
        return this;
    }

    public Fach removeFufu(KlasseFach klasseFach) {
        this.fufus.remove(klasseFach);
        klasseFach.setFach(null);
        return this;
    }

    public void setFufus(Set<KlasseFach> klasseFaches) {
        this.fufus = klasseFaches;
    }

    public Set<ZeugnisFach> getFaches() {
        return faches;
    }

    public Fach faches(Set<ZeugnisFach> zeugnisFaches) {
        this.faches = zeugnisFaches;
        return this;
    }

    public Fach addFach(ZeugnisFach zeugnisFach) {
        this.faches.add(zeugnisFach);
        zeugnisFach.setFach(this);
        return this;
    }

    public Fach removeFach(ZeugnisFach zeugnisFach) {
        this.faches.remove(zeugnisFach);
        zeugnisFach.setFach(null);
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
        Fach fach = (Fach) o;
        if (fach.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fach.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fach{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
