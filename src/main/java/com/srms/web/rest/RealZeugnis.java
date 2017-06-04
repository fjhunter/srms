package com.srms.web.rest;

import com.srms.domain.*;

/**
 * Created by fj on 04.06.17.
 */
public class RealZeugnis {
    private Schueler schueler;
    private Fach fach;
    private Klasse klasse;
    private ZeugnisFach note;
    private Zeugnis zeugnis;

    public RealZeugnis() {
    }

    public Schueler getSchueler() {
        return schueler;
    }

    public void setSchueler(Schueler schueler) {
        this.schueler = schueler;
    }

    public Fach getFach() {
        return fach;
    }

    public void setFach(Fach fach) {
        this.fach = fach;
    }

    public Klasse getKlasse() {
        return klasse;
    }

    public void setKlasse(Klasse klasse) {
        this.klasse = klasse;
    }

    public ZeugnisFach getNote() {
        return note;
    }

    public void setNote(ZeugnisFach note) {
        this.note = note;
    }

    public Zeugnis getZeugnis() {
        return zeugnis;
    }

    public void setZeugnis(Zeugnis zeugnis) {
        this.zeugnis = zeugnis;
    }
}
