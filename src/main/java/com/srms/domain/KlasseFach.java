package com.srms.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A KlasseFach.
 */
@Entity
@Table(name = "klasse_fach")
public class KlasseFach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Klasse klasse;

    @ManyToOne
    private Lehrer lehrer;

    @ManyToOne
    private Fach fach;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Klasse getKlasse() {
        return klasse;
    }

    public KlasseFach klasse(Klasse klasse) {
        this.klasse = klasse;
        return this;
    }

    public void setKlasse(Klasse klasse) {
        this.klasse = klasse;
    }

    public Lehrer getLehrer() {
        return lehrer;
    }

    public KlasseFach lehrer(Lehrer lehrer) {
        this.lehrer = lehrer;
        return this;
    }

    public void setLehrer(Lehrer lehrer) {
        this.lehrer = lehrer;
    }

    public Fach getFach() {
        return fach;
    }

    public KlasseFach fach(Fach fach) {
        this.fach = fach;
        return this;
    }

    public void setFach(Fach fach) {
        this.fach = fach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KlasseFach klasseFach = (KlasseFach) o;
        if (klasseFach.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), klasseFach.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KlasseFach{" +
            "id=" + getId() +
            "}";
    }
}
