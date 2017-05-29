import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    FehlzeitenMySuffixService,
    FehlzeitenMySuffixPopupService,
    FehlzeitenMySuffixComponent,
    FehlzeitenMySuffixDetailComponent,
    FehlzeitenMySuffixDialogComponent,
    FehlzeitenMySuffixPopupComponent,
    FehlzeitenMySuffixDeletePopupComponent,
    FehlzeitenMySuffixDeleteDialogComponent,
    fehlzeitenRoute,
    fehlzeitenPopupRoute,
} from './';

const ENTITY_STATES = [
    ...fehlzeitenRoute,
    ...fehlzeitenPopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FehlzeitenMySuffixComponent,
        FehlzeitenMySuffixDetailComponent,
        FehlzeitenMySuffixDialogComponent,
        FehlzeitenMySuffixDeleteDialogComponent,
        FehlzeitenMySuffixPopupComponent,
        FehlzeitenMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        FehlzeitenMySuffixComponent,
        FehlzeitenMySuffixDialogComponent,
        FehlzeitenMySuffixPopupComponent,
        FehlzeitenMySuffixDeleteDialogComponent,
        FehlzeitenMySuffixDeletePopupComponent,
    ],
    providers: [
        FehlzeitenMySuffixService,
        FehlzeitenMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsFehlzeitenMySuffixModule {}
