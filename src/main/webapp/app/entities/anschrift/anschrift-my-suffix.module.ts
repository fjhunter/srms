import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    AnschriftMySuffixService,
    AnschriftMySuffixPopupService,
    AnschriftMySuffixComponent,
    AnschriftMySuffixDetailComponent,
    AnschriftMySuffixDialogComponent,
    AnschriftMySuffixPopupComponent,
    AnschriftMySuffixDeletePopupComponent,
    AnschriftMySuffixDeleteDialogComponent,
    anschriftRoute,
    anschriftPopupRoute,
} from './';

const ENTITY_STATES = [
    ...anschriftRoute,
    ...anschriftPopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AnschriftMySuffixComponent,
        AnschriftMySuffixDetailComponent,
        AnschriftMySuffixDialogComponent,
        AnschriftMySuffixDeleteDialogComponent,
        AnschriftMySuffixPopupComponent,
        AnschriftMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        AnschriftMySuffixComponent,
        AnschriftMySuffixDialogComponent,
        AnschriftMySuffixPopupComponent,
        AnschriftMySuffixDeleteDialogComponent,
        AnschriftMySuffixDeletePopupComponent,
    ],
    providers: [
        AnschriftMySuffixService,
        AnschriftMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsAnschriftMySuffixModule {}
