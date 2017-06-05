import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PrintZeugnisComponent } from './print-zeugnis.component';

import { Principal } from '../../shared';

export const printZeugnisRoute: Routes = [
     {
        path: 'print-zeugnis/:id',
        component: PrintZeugnisComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_LEHRER'],
            pageTitle: 'srmsApp.printZeugnis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

