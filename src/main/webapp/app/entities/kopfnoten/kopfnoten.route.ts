import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { KopfnotenComponent } from './kopfnoten.component';

import { Principal } from '../../shared';

export const kopfnotenRoute: Routes = [
    {
        path: 'kopfnoten',
        component: KopfnotenComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.kopfnoten.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];
