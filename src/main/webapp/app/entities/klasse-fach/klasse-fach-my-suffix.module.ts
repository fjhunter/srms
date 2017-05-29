import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    KlasseFachMySuffixService,
    KlasseFachMySuffixPopupService,
    KlasseFachMySuffixComponent,
    KlasseFachMySuffixDetailComponent,
    KlasseFachMySuffixDialogComponent,
    KlasseFachMySuffixPopupComponent,
    KlasseFachMySuffixDeletePopupComponent,
    KlasseFachMySuffixDeleteDialogComponent,
    klasseFachRoute,
    klasseFachPopupRoute,
} from './';

const ENTITY_STATES = [
    ...klasseFachRoute,
    ...klasseFachPopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KlasseFachMySuffixComponent,
        KlasseFachMySuffixDetailComponent,
        KlasseFachMySuffixDialogComponent,
        KlasseFachMySuffixDeleteDialogComponent,
        KlasseFachMySuffixPopupComponent,
        KlasseFachMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        KlasseFachMySuffixComponent,
        KlasseFachMySuffixDialogComponent,
        KlasseFachMySuffixPopupComponent,
        KlasseFachMySuffixDeleteDialogComponent,
        KlasseFachMySuffixDeletePopupComponent,
    ],
    providers: [
        KlasseFachMySuffixService,
        KlasseFachMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsKlasseFachMySuffixModule {}
