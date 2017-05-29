import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FehlzeitenMySuffixComponent } from './fehlzeiten-my-suffix.component';
import { FehlzeitenMySuffixDetailComponent } from './fehlzeiten-my-suffix-detail.component';
import { FehlzeitenMySuffixPopupComponent } from './fehlzeiten-my-suffix-dialog.component';
import { FehlzeitenMySuffixDeletePopupComponent } from './fehlzeiten-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const fehlzeitenRoute: Routes = [
    {
        path: 'fehlzeiten-my-suffix',
        component: FehlzeitenMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.fehlzeiten.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'fehlzeiten-my-suffix/:id',
        component: FehlzeitenMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.fehlzeiten.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fehlzeitenPopupRoute: Routes = [
    {
        path: 'fehlzeiten-my-suffix-new',
        component: FehlzeitenMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.fehlzeiten.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fehlzeiten-my-suffix/:id/edit',
        component: FehlzeitenMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.fehlzeiten.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fehlzeiten-my-suffix/:id/delete',
        component: FehlzeitenMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.fehlzeiten.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
