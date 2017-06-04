package com.srms.web.rest;

import com.srms.domain.Klasse;
import com.srms.domain.Schueler;
import com.srms.domain.Zeugnis;

/**
 * Created by fj on 04.06.17.
 */
public class KopfNoten {
    private Klasse klasse;
    private Schueler schueler;
    private Zeugnis zeugnis;

    public KopfNoten() {
    }

    public KopfNoten(Klasse klasse, Schueler schueler, Zeugnis zeugnis) {
        this.klasse = klasse;
        this.schueler = schueler;
        this.zeugnis = zeugnis;
    }

    public Klasse getKlasse() {
        return klasse;
    }

    public void setKlasse(Klasse klasse) {
        this.klasse = klasse;
    }

    public Schueler getSchueler() {
        return schueler;
    }

    public void setSchueler(Schueler schueler) {
        this.schueler = schueler;
    }

    public Zeugnis getZeugnis() {
        return zeugnis;
    }

    public void setZeugnis(Zeugnis zeugnis) {
        this.zeugnis = zeugnis;
    }
}
