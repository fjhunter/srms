import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    PrintZeugnisService,
    PrintZeugnisComponent,
    printZeugnisRoute,
} from './';

const ENTITY_STATES = [
    ...printZeugnisRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PrintZeugnisComponent,
    ],
    entryComponents: [
        PrintZeugnisComponent,
    ],
    providers: [
        PrintZeugnisService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsPrintZeugnisModule {}
