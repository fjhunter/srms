
const enum Schulform {
    'HAUPTSCHULE',
    'REALSCHULE',
    'GYMNASIUM',
    'BERUFSCHULE',
    'FACHOBERSCHULE'

};
export class SchuelerMySuffix {
    constructor(
        public id?: number,
        public name?: string,
        public vorname?: string,
        public schulform?: Schulform,
        public schuelerId?: number,
        public fehlzeitenId?: number,
        public klasseId?: number,
        public anschriftId?: number,
    ) {
    }
}
