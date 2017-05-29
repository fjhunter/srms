package com.srms.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ZeugnisFach entity.
 */
public class ZeugnisFachDTO implements Serializable {

    private Long id;

    private Integer note;

    private Long zeugnisId;

    private Long fachId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Long getZeugnisId() {
        return zeugnisId;
    }

    public void setZeugnisId(Long zeugnisId) {
        this.zeugnisId = zeugnisId;
    }

    public Long getFachId() {
        return fachId;
    }

    public void setFachId(Long fachId) {
        this.fachId = fachId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZeugnisFachDTO zeugnisFachDTO = (ZeugnisFachDTO) o;
        if(zeugnisFachDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zeugnisFachDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZeugnisFachDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
