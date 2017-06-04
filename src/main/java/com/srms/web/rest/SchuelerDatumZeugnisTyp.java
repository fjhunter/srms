package com.srms.web.rest;

import com.srms.domain.enumeration.Zeugnis_typ;

/**
 * Created by fj on 03.06.17.
 */
public class SchuelerDatumZeugnisTyp {
    private Long schuelerId;
    private String datum;
    private Zeugnis_typ zeugnisTyp;

    public SchuelerDatumZeugnisTyp() {
    }

    public SchuelerDatumZeugnisTyp(Long schuelerId, String datum, Zeugnis_typ zeugnisTyp) {
        this.schuelerId = schuelerId;
        this.datum = datum;
        this.zeugnisTyp = zeugnisTyp;
    }

    public Long getSchuelerId() {
        return schuelerId;
    }

    public void setSchuelerId(Long schuelerId) {
        this.schuelerId = schuelerId;
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
