import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    ZeugnisMySuffixService,
    ZeugnisMySuffixPopupService,
    ZeugnisMySuffixComponent,
    ZeugnisMySuffixDetailComponent,
    ZeugnisMySuffixDialogComponent,
    ZeugnisMySuffixPopupComponent,
    ZeugnisMySuffixDeletePopupComponent,
    ZeugnisMySuffixDeleteDialogComponent,
    zeugnisRoute,
    zeugnisPopupRoute,
    ZeugnisMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...zeugnisRoute,
    ...zeugnisPopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ZeugnisMySuffixComponent,
        ZeugnisMySuffixDetailComponent,
        ZeugnisMySuffixDialogComponent,
        ZeugnisMySuffixDeleteDialogComponent,
        ZeugnisMySuffixPopupComponent,
        ZeugnisMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ZeugnisMySuffixComponent,
        ZeugnisMySuffixDialogComponent,
        ZeugnisMySuffixPopupComponent,
        ZeugnisMySuffixDeleteDialogComponent,
        ZeugnisMySuffixDeletePopupComponent,
    ],
    providers: [
        ZeugnisMySuffixService,
        ZeugnisMySuffixPopupService,
        ZeugnisMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsZeugnisMySuffixModule {}
