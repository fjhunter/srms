import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService} from 'ng-jhipster';

import {Kopfnoten} from './kopfnoten.model';
import {KopfnotenService} from './kopfnoten.service';
import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';
import {LehrerMySuffix} from "../lehrer/lehrer-my-suffix.model";
import {LehrerMySuffixService} from "../lehrer/lehrer-my-suffix.service";
import {ZeugnisMySuffix} from "../zeugnis/zeugnis-my-suffix.model";
import {ZeugnisMySuffixService} from "../zeugnis/zeugnis-my-suffix.service";

@Component({
    selector: 'jhi-kopfnoten',
    templateUrl: './kopfnoten.component.html'
})
export class KopfnotenComponent implements OnInit, OnDestroy {
    currentAccount: any;
    eventSubscriber: Subscription;
    selectedLehrer: LehrerMySuffix;
    lehrer: LehrerMySuffix[];
    kopfNoten: Kopfnoten[] = [];

    constructor(private zeugnisService: ZeugnisMySuffixService,
                private lehrerService: LehrerMySuffixService,
                private kopfnotenService: KopfnotenService,
                private alertService: AlertService,
                private eventManager: EventManager,
                private principal: Principal) {
    }

    ngOnInit() {
        this.loadLehrer();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            console.log(account);
        });
    }

    ngOnDestroy() {
    }

    loadLehrer() {
        this.lehrerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.lehrer = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadKopfNoten() {
        this.kopfnotenService.getKopfNoten(this.selectedLehrer.id).subscribe((res: ResponseWrapper) => {
            console.log(res.json);
            this.kopfNoten = res.json;
        })
    }

    updateZeugnisNote(kopfNote: Kopfnoten) {
        let valid: boolean = true;
        if(kopfNote.zeugnis.arbeitsverhalten == null && kopfNote.zeugnis.sozialverhalten == null)
            valid = false;
        if (kopfNote.zeugnis.arbeitsverhalten != null) {
            if (!this.validateNote(kopfNote.zeugnis.arbeitsverhalten)) {
                valid = false;
            }
        }
        if (kopfNote.zeugnis.sozialverhalten != null) {
            if (!this.validateNote(kopfNote.zeugnis.sozialverhalten)){
                valid = false;
            }
        }
        if(valid) {
            let zeugnis: ZeugnisMySuffix = new ZeugnisMySuffix();
            zeugnis.id = kopfNote.zeugnis.id;
            zeugnis.datum = kopfNote.zeugnis.datum;
            zeugnis.schuelerId = kopfNote.schueler.id;
            zeugnis.arbeitsverhalten = kopfNote.zeugnis.arbeitsverhalten;
            zeugnis.sozialverhalten = kopfNote.zeugnis.sozialverhalten;
            zeugnis.zeugnistyp = kopfNote.zeugnis.zeugnistyp;
            this.zeugnisService.update(zeugnis).toPromise().then(res => console.log(res));
        }
    }

    private validateNote(note: number): boolean {
        if (note <= 0 || note > 6) {
            alert("Bitte gebe Noten von 1 bis 6 ein!");
            return false
        }
        return true
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
