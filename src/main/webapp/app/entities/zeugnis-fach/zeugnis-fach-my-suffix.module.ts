import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    ZeugnisFachMySuffixService,
    ZeugnisFachMySuffixPopupService,
    ZeugnisFachMySuffixComponent,
    ZeugnisFachMySuffixDetailComponent,
    ZeugnisFachMySuffixDialogComponent,
    ZeugnisFachMySuffixPopupComponent,
    ZeugnisFachMySuffixDeletePopupComponent,
    ZeugnisFachMySuffixDeleteDialogComponent,
    zeugnisFachRoute,
    zeugnisFachPopupRoute,
} from './';

const ENTITY_STATES = [
    ...zeugnisFachRoute,
    ...zeugnisFachPopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ZeugnisFachMySuffixComponent,
        ZeugnisFachMySuffixDetailComponent,
        ZeugnisFachMySuffixDialogComponent,
        ZeugnisFachMySuffixDeleteDialogComponent,
        ZeugnisFachMySuffixPopupComponent,
        ZeugnisFachMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ZeugnisFachMySuffixComponent,
        ZeugnisFachMySuffixDialogComponent,
        ZeugnisFachMySuffixPopupComponent,
        ZeugnisFachMySuffixDeleteDialogComponent,
        ZeugnisFachMySuffixDeletePopupComponent,
    ],
    providers: [
        ZeugnisFachMySuffixService,
        ZeugnisFachMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsZeugnisFachMySuffixModule {}
