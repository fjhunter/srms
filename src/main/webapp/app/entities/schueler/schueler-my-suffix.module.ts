import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    SchuelerMySuffixService,
    SchuelerMySuffixPopupService,
    SchuelerMySuffixComponent,
    SchuelerMySuffixDetailComponent,
    SchuelerMySuffixDialogComponent,
    SchuelerMySuffixPopupComponent,
    SchuelerMySuffixDeletePopupComponent,
    SchuelerMySuffixDeleteDialogComponent,
    schuelerRoute,
    schuelerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...schuelerRoute,
    ...schuelerPopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SchuelerMySuffixComponent,
        SchuelerMySuffixDetailComponent,
        SchuelerMySuffixDialogComponent,
        SchuelerMySuffixDeleteDialogComponent,
        SchuelerMySuffixPopupComponent,
        SchuelerMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        SchuelerMySuffixComponent,
        SchuelerMySuffixDialogComponent,
        SchuelerMySuffixPopupComponent,
        SchuelerMySuffixDeleteDialogComponent,
        SchuelerMySuffixDeletePopupComponent,
    ],
    providers: [
        SchuelerMySuffixService,
        SchuelerMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsSchuelerMySuffixModule {}
