import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { KlasseFachMySuffix } from './klasse-fach-my-suffix.model';
import { KlasseFachMySuffixService } from './klasse-fach-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-klasse-fach-my-suffix',
    templateUrl: './klasse-fach-my-suffix.component.html'
})
export class KlasseFachMySuffixComponent implements OnInit, OnDestroy {
klasseFaches: KlasseFachMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private klasseFachService: KlasseFachMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.klasseFachService.query().subscribe(
            (res: ResponseWrapper) => {
                this.klasseFaches = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInKlasseFaches();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: KlasseFachMySuffix) {
        return item.id;
    }
    registerChangeInKlasseFaches() {
        this.eventSubscriber = this.eventManager.subscribe('klasseFachListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
