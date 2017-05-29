import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    LehrerMySuffixService,
    LehrerMySuffixPopupService,
    LehrerMySuffixComponent,
    LehrerMySuffixDetailComponent,
    LehrerMySuffixDialogComponent,
    LehrerMySuffixPopupComponent,
    LehrerMySuffixDeletePopupComponent,
    LehrerMySuffixDeleteDialogComponent,
    lehrerRoute,
    lehrerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...lehrerRoute,
    ...lehrerPopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LehrerMySuffixComponent,
        LehrerMySuffixDetailComponent,
        LehrerMySuffixDialogComponent,
        LehrerMySuffixDeleteDialogComponent,
        LehrerMySuffixPopupComponent,
        LehrerMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        LehrerMySuffixComponent,
        LehrerMySuffixDialogComponent,
        LehrerMySuffixPopupComponent,
        LehrerMySuffixDeleteDialogComponent,
        LehrerMySuffixDeletePopupComponent,
    ],
    providers: [
        LehrerMySuffixService,
        LehrerMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsLehrerMySuffixModule {}
