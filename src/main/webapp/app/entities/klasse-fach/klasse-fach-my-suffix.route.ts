import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { KlasseFachMySuffixComponent } from './klasse-fach-my-suffix.component';
import { KlasseFachMySuffixDetailComponent } from './klasse-fach-my-suffix-detail.component';
import { KlasseFachMySuffixPopupComponent } from './klasse-fach-my-suffix-dialog.component';
import { KlasseFachMySuffixDeletePopupComponent } from './klasse-fach-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const klasseFachRoute: Routes = [
    {
        path: 'klasse-fach-my-suffix',
        component: KlasseFachMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.klasseFach.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'klasse-fach-my-suffix/:id',
        component: KlasseFachMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.klasseFach.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const klasseFachPopupRoute: Routes = [
    {
        path: 'klasse-fach-my-suffix-new',
        component: KlasseFachMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.klasseFach.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'klasse-fach-my-suffix/:id/edit',
        component: KlasseFachMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.klasseFach.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'klasse-fach-my-suffix/:id/delete',
        component: KlasseFachMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.klasseFach.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
