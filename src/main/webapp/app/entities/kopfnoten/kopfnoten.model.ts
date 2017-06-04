import {SchuelerMySuffix} from "../schueler/schueler-my-suffix.model";
import {ZeugnisFachMySuffix} from "../zeugnis-fach/zeugnis-fach-my-suffix.model";
import {ZeugnisMySuffix} from "../zeugnis/zeugnis-my-suffix.model";
import {KlasseMySuffix} from "../klasse/klasse-my-suffix.model";
export class Kopfnoten {
    private _schueler: SchuelerMySuffix;
    private _zeugnis: ZeugnisMySuffix;
    private _klasse: KlasseMySuffix;


    constructor() {
    }


    get schueler(): SchuelerMySuffix {
        return this._schueler;
    }

    set schueler(value: SchuelerMySuffix) {
        this._schueler = value;
    }

    get zeugnis(): ZeugnisMySuffix {
        return this._zeugnis;
    }

    set zeugnis(value: ZeugnisMySuffix) {
        this._zeugnis = value;
    }

    get klasse(): KlasseMySuffix {
        return this._klasse;
    }

    set klasse(value: KlasseMySuffix) {
        this._klasse = value;
    }
}
