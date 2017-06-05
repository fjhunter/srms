import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SchuelerMySuffixComponent } from './schueler-my-suffix.component';
import { SchuelerMySuffixDetailComponent } from './schueler-my-suffix-detail.component';
import { SchuelerMySuffixPopupComponent } from './schueler-my-suffix-dialog.component';
import { SchuelerMySuffixDeletePopupComponent } from './schueler-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const schuelerRoute: Routes = [
    {
        path: 'schueler-my-suffix',
        component: SchuelerMySuffixComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SEKTETERIAT'],
            pageTitle: 'srmsApp.schueler.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'schueler-my-suffix/:id',
        component: SchuelerMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.schueler.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schuelerPopupRoute: Routes = [
    {
        path: 'schueler-my-suffix-new',
        component: SchuelerMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.schueler.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'schueler-my-suffix/:id/edit',
        component: SchuelerMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.schueler.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'schueler-my-suffix/:id/delete',
        component: SchuelerMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.schueler.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
