package com.srms.web.rest;

import com.srms.domain.enumeration.Zeugnis_typ;

/**
 * Created by fj on 03.06.17.
 */
public class SchuelerDatumZeugnisTyp {
    private Long lehrerId;
    private String datum;
    private Zeugnis_typ zeugnisTyp;

    public SchuelerDatumZeugnisTyp() {
    }

    public Long getLehrerId() {
        return lehrerId;
    }

    public void setLehrerId(Long lehrerId) {
        this.lehrerId = lehrerId;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Zeugnis_typ getZeugnisTyp() {
        return zeugnisTyp;
    }

    public void setZeugnisTyp(Zeugnis_typ zeugnisTyp) {
        this.zeugnisTyp = zeugnisTyp;
    }
}
