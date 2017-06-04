import {KlasseMySuffix} from "../klasse/klasse-my-suffix.model";
import {FachMySuffix} from "../fach/fach-my-suffix.model";
export class FachKlasseName {
    private _klasse: KlasseMySuffix;
    private _fach: FachMySuffix;


    constructor() {
    }

    get klasse(): KlasseMySuffix {
        return this._klasse;
    }

    set klasse(value: KlasseMySuffix) {
        this._klasse = value;
    }

    get fach(): FachMySuffix {
        return this._fach;
    }

    set fach(value: FachMySuffix) {
        this._fach = value;
    }
}
