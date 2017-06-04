import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FachMySuffixComponent } from './fach-my-suffix.component';
import { FachMySuffixDetailComponent } from './fach-my-suffix-detail.component';
import { FachMySuffixPopupComponent } from './fach-my-suffix-dialog.component';
import { FachMySuffixDeletePopupComponent } from './fach-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const fachRoute: Routes = [
    {
        path: 'fach-my-suffix',
        component: FachMySuffixComponent,
        data: {
            authorities: ['ROLE_USER', 'SEKETERIAT'],
            pageTitle: 'srmsApp.fach.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'fach-my-suffix/:id',
        component: FachMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.fach.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fachPopupRoute: Routes = [
    {
        path: 'fach-my-suffix-new',
        component: FachMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.fach.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fach-my-suffix/:id/edit',
        component: FachMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.fach.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fach-my-suffix/:id/delete',
        component: FachMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.fach.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
