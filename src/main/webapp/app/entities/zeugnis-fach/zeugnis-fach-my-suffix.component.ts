import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ZeugnisFachMySuffix } from './zeugnis-fach-my-suffix.model';
import { ZeugnisFachMySuffixService } from './zeugnis-fach-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-zeugnis-fach-my-suffix',
    templateUrl: './zeugnis-fach-my-suffix.component.html'
})
export class ZeugnisFachMySuffixComponent implements OnInit, OnDestroy {
zeugnisFaches: ZeugnisFachMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private zeugnisFachService: ZeugnisFachMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.zeugnisFachService.query().subscribe(
            (res: ResponseWrapper) => {
                this.zeugnisFaches = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInZeugnisFaches();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ZeugnisFachMySuffix) {
        return item.id;
    }
    registerChangeInZeugnisFaches() {
        this.eventSubscriber = this.eventManager.subscribe('zeugnisFachListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
