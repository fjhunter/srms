import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { LehrerMySuffixComponent } from './lehrer-my-suffix.component';
import { LehrerMySuffixDetailComponent } from './lehrer-my-suffix-detail.component';
import { LehrerMySuffixPopupComponent } from './lehrer-my-suffix-dialog.component';
import { LehrerMySuffixDeletePopupComponent } from './lehrer-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const lehrerRoute: Routes = [
    {
        path: 'lehrer-my-suffix',
        component: LehrerMySuffixComponent,
        data: {
            authorities: ['ROLE_USER', 'SEKETERIAT'],
            pageTitle: 'srmsApp.lehrer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lehrer-my-suffix/:id',
        component: LehrerMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.lehrer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lehrerPopupRoute: Routes = [
    {
        path: 'lehrer-my-suffix-new',
        component: LehrerMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.lehrer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lehrer-my-suffix/:id/edit',
        component: LehrerMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.lehrer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lehrer-my-suffix/:id/delete',
        component: LehrerMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.lehrer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
