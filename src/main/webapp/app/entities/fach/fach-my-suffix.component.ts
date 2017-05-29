import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { FachMySuffix } from './fach-my-suffix.model';
import { FachMySuffixService } from './fach-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-fach-my-suffix',
    templateUrl: './fach-my-suffix.component.html'
})
export class FachMySuffixComponent implements OnInit, OnDestroy {
faches: FachMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fachService: FachMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.fachService.query().subscribe(
            (res: ResponseWrapper) => {
                this.faches = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFaches();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FachMySuffix) {
        return item.id;
    }
    registerChangeInFaches() {
        this.eventSubscriber = this.eventManager.subscribe('fachListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
