import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { PrintZeugnis } from './print-zeugnis.model';
import { PrintZeugnisService } from './print-zeugnis.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {CompleteZeugnis} from "./complete-zeugnis.model";

@Component({
    selector: 'jhi-print-zeugnis',
    templateUrl: './print-zeugnis.component.html'
})
export class PrintZeugnisComponent implements OnInit, OnDestroy {
    zeugnis: CompleteZeugnis;
    currentAccount: any;
    eventSubscriber: Subscription;
    schulform: any;
    datum: string;
    private subscription: Subscription;
    constructor(
        private printZeugnisService: PrintZeugnisService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private route: ActivatedRoute,

    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }
    load(id) {
        this.printZeugnisService.find(id).subscribe((printZeugnis) => {
            console.log(printZeugnis);
            this.zeugnis = printZeugnis;
            this.schulform = printZeugnis.schueler.schulform;
        });
    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PrintZeugnis) {
        return item.id;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
