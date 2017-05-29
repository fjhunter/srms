import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SrmsSchuelerMySuffixModule } from './schueler/schueler-my-suffix.module';
import { SrmsZeugnisMySuffixModule } from './zeugnis/zeugnis-my-suffix.module';
import { SrmsSchulformMySuffixModule } from './schulform/schulform-my-suffix.module';
import { SrmsKlasseMySuffixModule } from './klasse/klasse-my-suffix.module';
import { SrmsLehrerMySuffixModule } from './lehrer/lehrer-my-suffix.module';
import { SrmsFehlzeitenMySuffixModule } from './fehlzeiten/fehlzeiten-my-suffix.module';
import { SrmsFachMySuffixModule } from './fach/fach-my-suffix.module';
import { SrmsAnschriftMySuffixModule } from './anschrift/anschrift-my-suffix.module';
import { SrmsZeugnisFachMySuffixModule } from './zeugnis-fach/zeugnis-fach-my-suffix.module';
import { SrmsKlasseFachMySuffixModule } from './klasse-fach/klasse-fach-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SrmsSchuelerMySuffixModule,
        SrmsZeugnisMySuffixModule,
        SrmsSchulformMySuffixModule,
        SrmsKlasseMySuffixModule,
        SrmsLehrerMySuffixModule,
        SrmsFehlzeitenMySuffixModule,
        SrmsFachMySuffixModule,
        SrmsAnschriftMySuffixModule,
        SrmsZeugnisFachMySuffixModule,
        SrmsKlasseFachMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsEntityModule {}
