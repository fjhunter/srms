import {Component, OnInit, OnDestroy, Input} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import 'rxjs/add/operator/toPromise';
import {EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService} from 'ng-jhipster';

import {CreateZeugnis} from './create-zeugnis.model';
import {LehrerMySuffix} from '../lehrer/lehrer-my-suffix.model'
import {CreateZeugnisService} from './create-zeugnis.service';
import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';
import {LehrerMySuffixService} from '../lehrer/lehrer-my-suffix.service';
import {KlasseFachMySuffixService} from '../klasse-fach/klasse-fach-my-suffix.service';
import {KlasseFachMySuffix} from "../klasse-fach/klasse-fach-my-suffix.model";
import {KlasseMySuffixService} from "../klasse/klasse-my-suffix.service";
import {FachMySuffixService} from "../fach/fach-my-suffix.service";
import {FachMySuffix} from "../fach/fach-my-suffix.model";
import {KlasseMySuffix} from "../klasse/klasse-my-suffix.model";
import {KlasseFachDto} from "../klasse-fach/klasse-fach-dto.model";
import {Observable} from "rxjs/Observable";
import {SchuelerMySuffixService} from "../schueler/schueler-my-suffix.service";
import {ZeugnisService} from "./zeugnis.zervice";
import {Zeugnis} from "./zeugnis.model";
import {SchuelerMySuffix} from "../schueler/schueler-my-suffix.model";
import {ZeugnisMySuffix} from "../zeugnis/zeugnis-my-suffix.model";
import {ZeugnisMySuffixService} from "../zeugnis/zeugnis-my-suffix.service";
import {ZeugnisSend} from "../zeugnis/zeugnis-send.model";
import {Zeugnis_typ} from "../zeugnis/zeugnis-typ.model";
import {FachKlasseName} from "./fach-klasse-name.model";
import {ZeugnisFachMySuffix} from "../zeugnis-fach/zeugnis-fach-my-suffix.model";
import {ZeugnisFachMySuffixService} from "../zeugnis-fach/zeugnis-fach-my-suffix.service";


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
    selectedZeugnisTyp: Zeugnis_typ;
    totalItems: number;
    selectedDate: any;
    zeugnise: Zeugnis[] = [];

    constructor(private fachService: FachMySuffixService,
                private zeugnisFachMySuffixService: ZeugnisFachMySuffixService,
                private klassService: KlasseMySuffixService,
                private klasseFachService: KlasseFachMySuffixService,
                private createZeugnisService: CreateZeugnisService,
                private alertService: AlertService,
                private eventManager: EventManager,
                private parseLinks: ParseLinks,
                private principal: Principal,
                private zeugnisService: ZeugnisMySuffixService,
                private schuelerService: SchuelerMySuffixService,
                private lehrerService: LehrerMySuffixService) {
        this.lehrerService = lehrerService;
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
        this.zeugnise = [];
        this.zeugnisService.getBySchueler(new ZeugnisSend(this.selectedLehrer.id, this.selectedDate, this.selectedZeugnisTyp)).toPromise().then(res => {
            this.zeugnise = res.json;
            console.log(this.zeugnise);
        });
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
                this.lehrer = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    getKlassName(id: number): Observable<KlasseMySuffix> {
        return this.klassService.find(id);
    }

    getFachName(id: number): Observable<FachMySuffix> {
        return this.fachService.find(id);
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
    updateZeugnisNote(zeugnis: Zeugnis) {
        let zeugnisFach: ZeugnisFachMySuffix = new ZeugnisFachMySuffix();
        zeugnisFach.zeugnisId = zeugnis.zeugnis.id;
        zeugnisFach.id = zeugnis.note.id;
        zeugnisFach.fachId = zeugnis.fach.id;
        zeugnisFach.note=zeugnis.note.note;
        this.zeugnisFachMySuffixService.updateNote(zeugnisFach).toPromise().then(res => console.log(res));
    }
}


// this.klasseFachZeugnis.getByLehrer(this.selectedLehrer.id).subscribe(
//     (res: ResponseWrapper) => {
//         let facher: KlasseFachMySuffix[] = res.json;
//         for (let i = 0; i < facher.length; i++) {
//             let fach: KlasseFachMySuffix = facher[i];
//             this.facher.push(new KlasseFachDto);
//             console.log(fach.id);
//             this.facher[facher.length-1].id = fach.id;
//             this.getKlassName(fach.klasseId).subscribe((res) => {
//                 this.facher[facher.length-1].klasse = res.name;
//                 this.getFachName(fach.fachId).subscribe((res) => {
//                     this.facher[facher.length-1].fach= res.name;
//                     console.log(this.facher);
//                 })
//
//             });
//
//         }
//     },
//     (res: ResponseWrapper) => this.onError(res.json)
// )
