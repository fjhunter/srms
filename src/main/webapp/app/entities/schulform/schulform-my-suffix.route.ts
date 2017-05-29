import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SchulformMySuffixComponent } from './schulform-my-suffix.component';
import { SchulformMySuffixDetailComponent } from './schulform-my-suffix-detail.component';
import { SchulformMySuffixPopupComponent } from './schulform-my-suffix-dialog.component';
import { SchulformMySuffixDeletePopupComponent } from './schulform-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const schulformRoute: Routes = [
    {
        path: 'schulform-my-suffix',
        component: SchulformMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.schulform.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'schulform-my-suffix/:id',
        component: SchulformMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.schulform.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schulformPopupRoute: Routes = [
    {
        path: 'schulform-my-suffix-new',
        component: SchulformMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.schulform.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'schulform-my-suffix/:id/edit',
        component: SchulformMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.schulform.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'schulform-my-suffix/:id/delete',
        component: SchulformMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.schulform.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
