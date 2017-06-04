
import {KlasseMySuffixService} from "../klasse/klasse-my-suffix.service";
import {ZeugnisMySuffix} from "../zeugnis/zeugnis-my-suffix.model";
import {SchuelerMySuffix} from "../schueler/schueler-my-suffix.model";
import {FachMySuffix} from "../fach/fach-my-suffix.model";
import {KlasseMySuffix} from "../klasse/klasse-my-suffix.model";
import {ZeugnisFachMySuffix} from "../zeugnis-fach/zeugnis-fach-my-suffix.model";
export class Zeugnis {
    private _schueler: SchuelerMySuffix;
    private _fach: FachMySuffix;
    private _klasse: KlasseMySuffix;
    private _noten: ZeugnisFachMySuffix;
    private _zeugnis: ZeugnisMySuffix;
    private _zeugnisFach: ZeugnisFachMySuffix;


    constructor() {
    }


    get schueler(): SchuelerMySuffix {
        return this._schueler;
    }

    set schueler(value: SchuelerMySuffix) {
        this._schueler = value;
    }

    get fach(): FachMySuffix {
        return this._fach;
    }

    set fach(value: FachMySuffix) {
        this._fach = value;
    }

    get klasse(): KlasseMySuffix {
        return this._klasse;
    }

    set klasse(value: KlasseMySuffix) {
        this._klasse = value;
    }

    get noten(): ZeugnisFachMySuffix {
        return this._noten;
    }

    set noten(value: ZeugnisFachMySuffix) {
        this._noten = value;
    }

    get zeugnisId(): ZeugnisMySuffix {
        return this._zeugnis;
    }

    set zeugnisId(value: ZeugnisMySuffix) {
        this._zeugnis = value;
    }


    get zeugnis(): ZeugnisMySuffix {
        return this._zeugnis;
    }

    set zeugnis(value: ZeugnisMySuffix) {
        this._zeugnis = value;
    }

    get zeugnisFach(): ZeugnisFachMySuffix {
        return this._zeugnisFach;
    }

    set zeugnisFach(value: ZeugnisFachMySuffix) {
        this._zeugnisFach = value;
    }
}
