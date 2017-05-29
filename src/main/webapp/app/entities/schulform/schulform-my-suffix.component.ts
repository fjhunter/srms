import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { SchulformMySuffix } from './schulform-my-suffix.model';
import { SchulformMySuffixService } from './schulform-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-schulform-my-suffix',
    templateUrl: './schulform-my-suffix.component.html'
})
export class SchulformMySuffixComponent implements OnInit, OnDestroy {
schulforms: SchulformMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private schulformService: SchulformMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.schulformService.query().subscribe(
            (res: ResponseWrapper) => {
                this.schulforms = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSchulforms();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SchulformMySuffix) {
        return item.id;
    }
    registerChangeInSchulforms() {
        this.eventSubscriber = this.eventManager.subscribe('schulformListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
