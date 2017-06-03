import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { CreateZeugnis } from './create-zeugnis.model';
import {LehrerMySuffix } from '../lehrer/lehrer-my-suffix.model'
import { CreateZeugnisService } from './create-zeugnis.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {LehrerMySuffixService} from '../lehrer/lehrer-my-suffix.service';
import {KlasseFachMySuffixService} from '../klasse-fach/klasse-fach-my-suffix.service';
import {KlasseFachMySuffix} from "../klasse-fach/klasse-fach-my-suffix.model";
import {KlasseMySuffixService} from "../klasse/klasse-my-suffix.service";
import {FachMySuffixService} from "../fach/fach-my-suffix.service";
import {FachMySuffix} from "../fach/fach-my-suffix.model";
import {KlasseMySuffix} from "../klasse/klasse-my-suffix.model";

@Component({
    selector: 'jhi-create-zeugnis',
    templateUrl: './create-zeugnis.component.html'
})
export class CreateZeugnisComponent implements OnInit, OnDestroy {

    createZeugnis: CreateZeugnis[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    lehrer: LehrerMySuffix[];
    selectedLehrer: LehrerMySuffix;
    totalItems: number;
    facher: KlasseFachMySuffix[];
    constructor(
        private fachService: FachMySuffixService,
        private klassService: KlasseMySuffixService,
        private klasseFachZeugnis: KlasseFachMySuffixService,
        private createZeugnisService: CreateZeugnisService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private parseLinks: ParseLinks,
        private principal: Principal,
        private lehrerService: LehrerMySuffixService
    ) {
        this.lehrerService=lehrerService;
        this.createZeugnis = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.createZeugnisService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadFacher() {
        this.klasseFachZeugnis.getByLehrer(this.selectedLehrer.id).subscribe(
            (res: ResponseWrapper) => {
                this.facher = res.json;
                console.log(res.json);
            },
            (res: ResponseWrapper) => this.onError(res.json)
        )
    }

    reset() {
        this.page = 0;
        this.createZeugnis = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }
    loadLehrer() {
        this.lehrerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.lehrer= res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    getKlassName(id: number): KlasseMySuffix {
        this.klassService.find(id).subscribe((res) => {
                return res;
            });
        return "";
    }

    getFachName(id: number): FachMySuffix {
        this.fachService.find(id).subscribe((res) => {
                return res;
            });
        return "";
    }

    ngOnInit() {
        this.loadAll();
        this.loadLehrer();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCreateZeugnis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CreateZeugnis) {
        return item.id;
    }
    registerChangeInCreateZeugnis() {
        this.eventSubscriber = this.eventManager.subscribe('createZeugnisListModification', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.createZeugnis.push(data[i]);
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
