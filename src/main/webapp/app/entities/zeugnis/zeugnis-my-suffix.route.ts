import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ZeugnisMySuffixComponent } from './zeugnis-my-suffix.component';
import { ZeugnisMySuffixDetailComponent } from './zeugnis-my-suffix-detail.component';
import { ZeugnisMySuffixPopupComponent } from './zeugnis-my-suffix-dialog.component';
import { ZeugnisMySuffixDeletePopupComponent } from './zeugnis-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ZeugnisMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const zeugnisRoute: Routes = [
    {
        path: 'zeugnis-my-suffix',
        component: ZeugnisMySuffixComponent,
        resolve: {
            'pagingParams': ZeugnisMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.zeugnis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'zeugnis-my-suffix/:id',
        component: ZeugnisMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.zeugnis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const zeugnisPopupRoute: Routes = [
    {
        path: 'zeugnis-my-suffix-new',
        component: ZeugnisMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.zeugnis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'zeugnis-my-suffix/:id/edit',
        component: ZeugnisMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.zeugnis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'zeugnis-my-suffix/:id/delete',
        component: ZeugnisMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'srmsApp.zeugnis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
