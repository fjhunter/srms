import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SrmsSharedModule } from '../../shared';
import {
    CreateZeugnisService,
    CreateZeugnisPopupService,
    CreateZeugnisComponent,
    CreateZeugnisDetailComponent,
    CreateZeugnisDialogComponent,
    CreateZeugnisPopupComponent,
    CreateZeugnisDeletePopupComponent,
    CreateZeugnisDeleteDialogComponent,
    createZeugnisRoute,
    createZeugnisPopupRoute,
} from './';

const ENTITY_STATES = [
    ...createZeugnisRoute,
    ...createZeugnisPopupRoute,
];

@NgModule({
    imports: [
        SrmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CreateZeugnisComponent,
        CreateZeugnisDetailComponent,
        CreateZeugnisDialogComponent,
        CreateZeugnisDeleteDialogComponent,
        CreateZeugnisPopupComponent,
        CreateZeugnisDeletePopupComponent,
    ],
    entryComponents: [
        CreateZeugnisComponent,
        CreateZeugnisDialogComponent,
        CreateZeugnisPopupComponent,
        CreateZeugnisDeleteDialogComponent,
        CreateZeugnisDeletePopupComponent,
    ],
    providers: [
        CreateZeugnisService,
        CreateZeugnisPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SrmsCreateZeugnisModule {}
