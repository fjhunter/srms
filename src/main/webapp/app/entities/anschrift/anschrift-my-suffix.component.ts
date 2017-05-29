import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { AnschriftMySuffix } from './anschrift-my-suffix.model';
import { AnschriftMySuffixService } from './anschrift-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-anschrift-my-suffix',
    templateUrl: './anschrift-my-suffix.component.html'
})
export class AnschriftMySuffixComponent implements OnInit, OnDestroy {
anschrifts: AnschriftMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private anschriftService: AnschriftMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.anschriftService.query().subscribe(
            (res: ResponseWrapper) => {
                this.anschrifts = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAnschrifts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AnschriftMySuffix) {
        return item.id;
    }
    registerChangeInAnschrifts() {
        this.eventSubscriber = this.eventManager.subscribe('anschriftListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
