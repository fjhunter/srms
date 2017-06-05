import {ZeugnisFachMySuffix} from "../zeugnis-fach/zeugnis-fach-my-suffix.model";
import {SchuelerMySuffix, Schuler} from "../schueler/schueler-my-suffix.model";
import {Zeugnis_typ} from "../zeugnis/zeugnis-typ.model";
export class CompleteZeugnis {
    private _facher: ZeugnisFachMySuffix[];
    private _schueler: Schuler;
    private _id: number;
    private _sozialverhalten: number;
    private _arbeitsverhalten:number;
    private _date: any;
    private _zeugnis_typ: Zeugnis_typ;


    constructor() {
    }

    get facher(): ZeugnisFachMySuffix[] {
        return this._facher;
    }

    set facher(value: ZeugnisFachMySuffix[]) {
        this._facher = value;
    }

    get schueler(): SchuelerMySuffix {
        return this._schueler;
    }

    set schueler(value: SchuelerMySuffix) {
        this._schueler = value;
    }

    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get sozialverhalten(): number {
        return this._sozialverhalten;
    }

    set sozialverhalten(value: number) {
        this._sozialverhalten = value;
    }

    get arbeitsverhalten(): number {
        return this._arbeitsverhalten;
    }

    set arbeitsverhalten(value: number) {
        this._arbeitsverhalten = value;
    }

    get date(): any {
        return this._date;
    }

    set date(value: any) {
        this._date = value;
    }

    get zeugnis_typ(): Zeugnis_typ {
        return this._zeugnis_typ;
    }

    set zeugnis_typ(value: Zeugnis_typ) {
        this._zeugnis_typ = value;
    }
}
