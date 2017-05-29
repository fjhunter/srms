import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { FehlzeitenMySuffix } from './fehlzeiten-my-suffix.model';
import { FehlzeitenMySuffixService } from './fehlzeiten-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-fehlzeiten-my-suffix',
    templateUrl: './fehlzeiten-my-suffix.component.html'
})
export class FehlzeitenMySuffixComponent implements OnInit, OnDestroy {
fehlzeitens: FehlzeitenMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fehlzeitenService: FehlzeitenMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.fehlzeitenService.query().subscribe(
            (res: ResponseWrapper) => {
                this.fehlzeitens = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFehlzeitens();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FehlzeitenMySuffix) {
        return item.id;
    }
    registerChangeInFehlzeitens() {
        this.eventSubscriber = this.eventManager.subscribe('fehlzeitenListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
