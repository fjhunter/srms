import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CreateZeugnisComponent } from './create-zeugnis.component';
import { CreateZeugnisDetailComponent } from './create-zeugnis-detail.component';
import { CreateZeugnisPopupComponent } from './create-zeugnis-dialog.component';
import { CreateZeugnisDeletePopupComponent } from './create-zeugnis-delete-dialog.component';

import { Principal } from '../../shared';

export const createZeugnisRoute: Routes = [
    {
        path: 'create-zeugnis',
        component: CreateZeugnisComponent,
        data: {
            authorities: ['ROLE_LEHRER'],
            pageTitle: 'srmsApp.createZeugnis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'create-zeugnis/:id',
        component: CreateZeugnisDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.createZeugnis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const createZeugnisPopupRoute: Routes = [
    {
        path: 'create-zeugnis-new',
        component: CreateZeugnisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.createZeugnis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'create-zeugnis/:id/edit',
        component: CreateZeugnisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.createZeugnis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'create-zeugnis/:id/delete',
        component: CreateZeugnisDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.createZeugnis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
