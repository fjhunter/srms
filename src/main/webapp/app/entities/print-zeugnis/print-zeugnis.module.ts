import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    PrintZeugnisService,
    PrintZeugnisPopupService,
    PrintZeugnisComponent,
    PrintZeugnisDetailComponent,
    PrintZeugnisDialogComponent,
    PrintZeugnisPopupComponent,
    PrintZeugnisDeletePopupComponent,
    PrintZeugnisDeleteDialogComponent,
    printZeugnisRoute,
    printZeugnisPopupRoute,
} from './';

const ENTITY_STATES = [
    ...printZeugnisRoute,
    ...printZeugnisPopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PrintZeugnisComponent,
        PrintZeugnisDetailComponent,
        PrintZeugnisDialogComponent,
        PrintZeugnisDeleteDialogComponent,
        PrintZeugnisPopupComponent,
        PrintZeugnisDeletePopupComponent,
    ],
    entryComponents: [
        PrintZeugnisComponent,
        PrintZeugnisDialogComponent,
        PrintZeugnisPopupComponent,
        PrintZeugnisDeleteDialogComponent,
        PrintZeugnisDeletePopupComponent,
    ],
    providers: [
        PrintZeugnisService,
        PrintZeugnisPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsPrintZeugnisModule {}
