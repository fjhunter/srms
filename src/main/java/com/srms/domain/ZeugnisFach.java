package com.srms.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ZeugnisFach.
 */
@Entity
@Table(name = "zeugnis_fach")
public class ZeugnisFach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "note")
    private Integer note;

    @ManyToOne
    private Zeugnis zeugnis;

    @ManyToOne
    private Fach fach;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNote() {
        return note;
    }

    public ZeugnisFach note(Integer note) {
        this.note = note;
        return this;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Zeugnis getZeugnis() {
        return zeugnis;
    }

    public ZeugnisFach zeugnis(Zeugnis zeugnis) {
        this.zeugnis = zeugnis;
        return this;
    }

    public void setZeugnis(Zeugnis zeugnis) {
        this.zeugnis = zeugnis;
    }

    public Fach getFach() {
        return fach;
    }

    public ZeugnisFach fach(Fach fach) {
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
        ZeugnisFach zeugnisFach = (ZeugnisFach) o;
        if (zeugnisFach.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zeugnisFach.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZeugnisFach{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
