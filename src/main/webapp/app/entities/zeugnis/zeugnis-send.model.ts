import {Zeugnis_typ} from "./zeugnis-typ.model";
export class ZeugnisSend {
    private _schuelerId: number;
    private _datum: any;
    private _zeugnisTyp: Zeugnis_typ;


    constructor(schuelerId: number, datum: any, zeugnisTyp: Zeugnis_typ) {
        this._schuelerId = schuelerId;
        this._datum = datum;
        this._zeugnisTyp = zeugnisTyp;
    }


    get schuelerId(): number {
        return this._schuelerId;
    }

    set schuelerId(value: number) {
        this._schuelerId = value;
    }

    get datum(): any {
        return this._datum;
    }

    set datum(value: any) {
        this._datum = value;
    }

    get zeugnisTyp(): Zeugnis_typ {
        return this._zeugnisTyp;
    }

    set zeugnisTyp(value: Zeugnis_typ) {
        this._zeugnisTyp = value;
    }
}
