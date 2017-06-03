import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { PrintZeugnis } from './print-zeugnis.model';
import { PrintZeugnisService } from './print-zeugnis.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-print-zeugnis',
    templateUrl: './print-zeugnis.component.html'
})
export class PrintZeugnisComponent implements OnInit, OnDestroy {
printZeugnis: PrintZeugnis[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private printZeugnisService: PrintZeugnisService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.printZeugnisService.query().subscribe(
            (res: ResponseWrapper) => {
                this.printZeugnis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrintZeugnis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PrintZeugnis) {
        return item.id;
    }
    registerChangeInPrintZeugnis() {
        this.eventSubscriber = this.eventManager.subscribe('printZeugnisListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
