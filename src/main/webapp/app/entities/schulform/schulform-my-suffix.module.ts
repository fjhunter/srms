import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    SchulformMySuffixService,
    SchulformMySuffixPopupService,
    SchulformMySuffixComponent,
    SchulformMySuffixDetailComponent,
    SchulformMySuffixDialogComponent,
    SchulformMySuffixPopupComponent,
    SchulformMySuffixDeletePopupComponent,
    SchulformMySuffixDeleteDialogComponent,
    schulformRoute,
    schulformPopupRoute,
} from './';

const ENTITY_STATES = [
    ...schulformRoute,
    ...schulformPopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SchulformMySuffixComponent,
        SchulformMySuffixDetailComponent,
        SchulformMySuffixDialogComponent,
        SchulformMySuffixDeleteDialogComponent,
        SchulformMySuffixPopupComponent,
        SchulformMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        SchulformMySuffixComponent,
        SchulformMySuffixDialogComponent,
        SchulformMySuffixPopupComponent,
        SchulformMySuffixDeleteDialogComponent,
        SchulformMySuffixDeletePopupComponent,
    ],
    providers: [
        SchulformMySuffixService,
        SchulformMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsSchulformMySuffixModule {}
