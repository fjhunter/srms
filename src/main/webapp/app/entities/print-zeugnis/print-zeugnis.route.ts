import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PrintZeugnisComponent } from './print-zeugnis.component';
import { PrintZeugnisDetailComponent } from './print-zeugnis-detail.component';
import { PrintZeugnisPopupComponent } from './print-zeugnis-dialog.component';
import { PrintZeugnisDeletePopupComponent } from './print-zeugnis-delete-dialog.component';

import { Principal } from '../../shared';

export const printZeugnisRoute: Routes = [
    {
        path: 'print-zeugnis',
        component: PrintZeugnisComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.printZeugnis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'print-zeugnis/:id',
        component: PrintZeugnisDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.printZeugnis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const printZeugnisPopupRoute: Routes = [
    {
        path: 'print-zeugnis-new',
        component: PrintZeugnisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.printZeugnis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'print-zeugnis/:id/edit',
        component: PrintZeugnisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.printZeugnis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'print-zeugnis/:id/delete',
        component: PrintZeugnisDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.printZeugnis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
