import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { KlasseMySuffix } from './klasse-my-suffix.model';
import { KlasseMySuffixService } from './klasse-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-klasse-my-suffix',
    templateUrl: './klasse-my-suffix.component.html'
})
export class KlasseMySuffixComponent implements OnInit, OnDestroy {
klasses: KlasseMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private klasseService: KlasseMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.klasseService.query().subscribe(
            (res: ResponseWrapper) => {
                this.klasses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInKlasses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: KlasseMySuffix) {
        return item.id;
    }
    registerChangeInKlasses() {
        this.eventSubscriber = this.eventManager.subscribe('klasseListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
