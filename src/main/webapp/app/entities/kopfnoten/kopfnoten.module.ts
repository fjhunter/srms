import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    KopfnotenService,
    KopfnotenComponent,
    kopfnotenRoute,
} from './';

const ENTITY_STATES = [
    ...kopfnotenRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KopfnotenComponent,
    ],
    entryComponents: [
        KopfnotenComponent,
    ],
    providers: [
        KopfnotenService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsKopfnotenModule {}
