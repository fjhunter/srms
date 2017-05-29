import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    KlasseMySuffixService,
    KlasseMySuffixPopupService,
    KlasseMySuffixComponent,
    KlasseMySuffixDetailComponent,
    KlasseMySuffixDialogComponent,
    KlasseMySuffixPopupComponent,
    KlasseMySuffixDeletePopupComponent,
    KlasseMySuffixDeleteDialogComponent,
    klasseRoute,
    klassePopupRoute,
} from './';

const ENTITY_STATES = [
    ...klasseRoute,
    ...klassePopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KlasseMySuffixComponent,
        KlasseMySuffixDetailComponent,
        KlasseMySuffixDialogComponent,
        KlasseMySuffixDeleteDialogComponent,
        KlasseMySuffixPopupComponent,
        KlasseMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        KlasseMySuffixComponent,
        KlasseMySuffixDialogComponent,
        KlasseMySuffixPopupComponent,
        KlasseMySuffixDeleteDialogComponent,
        KlasseMySuffixDeletePopupComponent,
    ],
    providers: [
        KlasseMySuffixService,
        KlasseMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsKlasseMySuffixModule {}
