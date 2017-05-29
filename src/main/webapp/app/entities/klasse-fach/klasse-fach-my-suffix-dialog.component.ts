import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { KlasseFachMySuffix } from './klasse-fach-my-suffix.model';
import { KlasseFachMySuffixPopupService } from './klasse-fach-my-suffix-popup.service';
import { KlasseFachMySuffixService } from './klasse-fach-my-suffix.service';
import { KlasseMySuffix, KlasseMySuffixService } from '../klasse';
import { LehrerMySuffix, LehrerMySuffixService } from '../lehrer';
import { FachMySuffix, FachMySuffixService } from '../fach';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-klasse-fach-my-suffix-dialog',
    templateUrl: './klasse-fach-my-suffix-dialog.component.html'
})
export class KlasseFachMySuffixDialogComponent implements OnInit {

    klasseFach: KlasseFachMySuffix;
    authorities: any[];
    isSaving: boolean;

    klasses: KlasseMySuffix[];

    lehrers: LehrerMySuffix[];

    faches: FachMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private klasseFachService: KlasseFachMySuffixService,
        private klasseService: KlasseMySuffixService,
        private lehrerService: LehrerMySuffixService,
        private fachService: FachMySuffixService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.klasseService.query()
            .subscribe((res: ResponseWrapper) => { this.klasses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.lehrerService.query()
            .subscribe((res: ResponseWrapper) => { this.lehrers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.fachService.query()
            .subscribe((res: ResponseWrapper) => { this.faches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.klasseFach.id !== undefined) {
            this.subscribeToSaveResponse(
                this.klasseFachService.update(this.klasseFach));
        } else {
            this.subscribeToSaveResponse(
                this.klasseFachService.create(this.klasseFach));
        }
    }

    private subscribeToSaveResponse(result: Observable<KlasseFachMySuffix>) {
        result.subscribe((res: KlasseFachMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: KlasseFachMySuffix) {
        this.eventManager.broadcast({ name: 'klasseFachListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackKlasseById(index: number, item: KlasseMySuffix) {
        return item.id;
    }

    trackLehrerById(index: number, item: LehrerMySuffix) {
        return item.id;
    }

    trackFachById(index: number, item: FachMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-klasse-fach-my-suffix-popup',
    template: ''
})
export class KlasseFachMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private klasseFachPopupService: KlasseFachMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.klasseFachPopupService
                    .open(KlasseFachMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.klasseFachPopupService
                    .open(KlasseFachMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
