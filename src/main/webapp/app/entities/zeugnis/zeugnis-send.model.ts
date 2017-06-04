import {Zeugnis_typ} from "./zeugnis-typ.model";
export class ZeugnisSend {
    private _lehrerId: number;
    private _datum: any;
    private _zeugnisTyp: Zeugnis_typ;


    constructor(lehrerId: number, datum: any, zeugnisTyp: Zeugnis_typ) {
        this._lehrerId = lehrerId;
        this._datum = datum;
        this._zeugnisTyp = zeugnisTyp;
    }

    get lehrerId(): number {
        return this._lehrerId;
    }

    set lehrerId(value: number) {
        this._lehrerId = value;
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
