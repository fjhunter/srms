package com.srms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Schulform.
 */
@Entity
@Table(name = "schulform")
public class Schulform implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "bezeichnung", nullable = false)
    private String bezeichnung;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public Schulform bezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
        return this;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schulform schulform = (Schulform) o;
        if (schulform.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schulform.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Schulform{" +
            "id=" + getId() +
            ", bezeichnung='" + getBezeichnung() + "'" +
            "}";
    }
}
