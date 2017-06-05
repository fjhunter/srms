import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { KlasseMySuffixComponent } from './klasse-my-suffix.component';
import { KlasseMySuffixDetailComponent } from './klasse-my-suffix-detail.component';
import { KlasseMySuffixPopupComponent } from './klasse-my-suffix-dialog.component';
import { KlasseMySuffixDeletePopupComponent } from './klasse-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const klasseRoute: Routes = [
    {
        path: 'klasse-my-suffix',
        component: KlasseMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.klasse.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'klasse-my-suffix/:id',
        component: KlasseMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.klasse.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const klassePopupRoute: Routes = [
    {
        path: 'klasse-my-suffix-new',
        component: KlasseMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.klasse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'klasse-my-suffix/:id/edit',
        component: KlasseMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.klasse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'klasse-my-suffix/:id/delete',
        component: KlasseMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.klasse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
