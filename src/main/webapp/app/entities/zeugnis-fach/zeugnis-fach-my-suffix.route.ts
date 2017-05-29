import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ZeugnisFachMySuffixComponent } from './zeugnis-fach-my-suffix.component';
import { ZeugnisFachMySuffixDetailComponent } from './zeugnis-fach-my-suffix-detail.component';
import { ZeugnisFachMySuffixPopupComponent } from './zeugnis-fach-my-suffix-dialog.component';
import { ZeugnisFachMySuffixDeletePopupComponent } from './zeugnis-fach-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const zeugnisFachRoute: Routes = [
    {
        path: 'zeugnis-fach-my-suffix',
        component: ZeugnisFachMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.zeugnisFach.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'zeugnis-fach-my-suffix/:id',
        component: ZeugnisFachMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.zeugnisFach.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const zeugnisFachPopupRoute: Routes = [
    {
        path: 'zeugnis-fach-my-suffix-new',
        component: ZeugnisFachMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.zeugnisFach.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'zeugnis-fach-my-suffix/:id/edit',
        component: ZeugnisFachMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.zeugnisFach.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'zeugnis-fach-my-suffix/:id/delete',
        component: ZeugnisFachMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.zeugnisFach.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
