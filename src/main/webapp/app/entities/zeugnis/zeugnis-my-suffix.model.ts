
const enum Zeugnis_typ {
    'HALBJAHRESZEUGNIS',
    'ZEUGNIS',
    'ABSCHLUSSZEUGNISS'

};
export class ZeugnisMySuffix {
    constructor(
        public id?: number,
        public sozialverhalten?: number,
        public arbeitsverhalten?: number,
        public datum?: any,
        public zeugnistyp?: Zeugnis_typ,
        public schuelerId?: number,
        public fachId?: number,
    ) {
    }
}
