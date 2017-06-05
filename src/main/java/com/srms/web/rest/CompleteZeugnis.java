package com.srms.web.rest;

import com.srms.domain.Schueler;
import com.srms.domain.Zeugnis;
import com.srms.domain.ZeugnisFach;
import com.srms.domain.enumeration.Zeugnis_typ;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by fj on 05.06.17.
 */
public class CompleteZeugnis {
    private List<ZeugnisFach> facher;
    private Schueler schueler;
    private Long id;
    private Integer sozialverhalten;
    private Integer arbeitsverhalten;
    private ZonedDateTime date;
    private Zeugnis_typ zeugnis_typ;

    public CompleteZeugnis() {
    }

    public CompleteZeugnis(Zeugnis zeugnis) {
        this.schueler = zeugnis.getSchueler();
        this.id = zeugnis.getId();
        this.sozialverhalten = zeugnis.getSozialverhalten();
        this.arbeitsverhalten = zeugnis.getArbeitsverhalten();
        this.date = zeugnis.getDatum();
        this.zeugnis_typ = zeugnis.getZeugnistyp();
    }

    public List<ZeugnisFach> getFacher() {
        return facher;
    }

    public void setFacher(List<ZeugnisFach> facher) {
        this.facher = facher;
    }

    public Schueler getSchueler() {
        return schueler;
    }

    public void setSchueler(Schueler schueler) {
        this.schueler = schueler;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSozialverhalten() {
        return sozialverhalten;
    }

    public void setSozialverhalten(Integer sozialverhalten) {
        this.sozialverhalten = sozialverhalten;
    }

    public Integer getArbeitsverhalten() {
        return arbeitsverhalten;
    }

    public void setArbeitsverhalten(Integer arbeitsverhalten) {
        this.arbeitsverhalten = arbeitsverhalten;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Zeugnis_typ getZeugnis_typ() {
        return zeugnis_typ;
    }

    public void setZeugnis_typ(Zeugnis_typ zeugnis_typ) {
        this.zeugnis_typ = zeugnis_typ;
    }
}
