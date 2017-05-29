import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { SchuelerMySuffix } from './schueler-my-suffix.model';
import { SchuelerMySuffixService } from './schueler-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-schueler-my-suffix',
    templateUrl: './schueler-my-suffix.component.html'
})
export class SchuelerMySuffixComponent implements OnInit, OnDestroy {
schuelers: SchuelerMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private schuelerService: SchuelerMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.schuelerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.schuelers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSchuelers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SchuelerMySuffix) {
        return item.id;
    }
    registerChangeInSchuelers() {
        this.eventSubscriber = this.eventManager.subscribe('schuelerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
