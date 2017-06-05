import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AnschriftMySuffixComponent } from './anschrift-my-suffix.component';
import { AnschriftMySuffixDetailComponent } from './anschrift-my-suffix-detail.component';
import { AnschriftMySuffixPopupComponent } from './anschrift-my-suffix-dialog.component';
import { AnschriftMySuffixDeletePopupComponent } from './anschrift-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const anschriftRoute: Routes = [
    {
        path: 'anschrift-my-suffix',
        component: AnschriftMySuffixComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SEKTETERIAT'],
            pageTitle: 'srmsApp.anschrift.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'anschrift-my-suffix/:id',
        component: AnschriftMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.anschrift.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const anschriftPopupRoute: Routes = [
    {
        path: 'anschrift-my-suffix-new',
        component: AnschriftMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.anschrift.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anschrift-my-suffix/:id/edit',
        component: AnschriftMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.anschrift.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anschrift-my-suffix/:id/delete',
        component: AnschriftMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.anschrift.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
