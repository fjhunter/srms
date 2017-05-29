import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { LehrerMySuffix } from './lehrer-my-suffix.model';
import { LehrerMySuffixService } from './lehrer-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-lehrer-my-suffix',
    templateUrl: './lehrer-my-suffix.component.html'
})
export class LehrerMySuffixComponent implements OnInit, OnDestroy {
lehrers: LehrerMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private lehrerService: LehrerMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.lehrerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.lehrers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLehrers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LehrerMySuffix) {
        return item.id;
    }
    registerChangeInLehrers() {
        this.eventSubscriber = this.eventManager.subscribe('lehrerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
