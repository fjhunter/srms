import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    FachMySuffixService,
    FachMySuffixPopupService,
    FachMySuffixComponent,
    FachMySuffixDetailComponent,
    FachMySuffixDialogComponent,
    FachMySuffixPopupComponent,
    FachMySuffixDeletePopupComponent,
    FachMySuffixDeleteDialogComponent,
    fachRoute,
    fachPopupRoute,
} from './';

const ENTITY_STATES = [
    ...fachRoute,
    ...fachPopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FachMySuffixComponent,
        FachMySuffixDetailComponent,
        FachMySuffixDialogComponent,
        FachMySuffixDeleteDialogComponent,
        FachMySuffixPopupComponent,
        FachMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        FachMySuffixComponent,
        FachMySuffixDialogComponent,
        FachMySuffixPopupComponent,
        FachMySuffixDeleteDialogComponent,
        FachMySuffixDeletePopupComponent,
    ],
    providers: [
        FachMySuffixService,
        FachMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsFachMySuffixModule {}
