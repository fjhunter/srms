import {KlasseMySuffix} from "../klasse/klasse-my-suffix.model";
import {AnschriftMySuffix} from "../anschrift/anschrift-my-suffix.model";
const enum Schulform {
    'HAUPTSCHULE',
    'REALSCHULE',
    'GYMNASIUM',
    'BERUFSCHULE',
    'FACHOBERSCHULE'

}
;
export class SchuelerMySuffix {
    constructor(public id?: number,
                public name?: string,
                public vorname?: string,
                public schulform?: Schulform,
                public schuelerId?: number,
                public fehlzeitenId?: number,
                public klasseId?: number,
                public anschriftId?: number,) {
    }

}
export class Schuler {
    constructor(public id?: number,
                public name?: string,
                public vorname?: string,
                public schulform?: Schulform,
                public schuelerId?: number,
                public fehlzeitenId?: number,
                public klasse?: KlasseMySuffix,
                public anschrift?: AnschriftMySuffix,) {
    }
}
