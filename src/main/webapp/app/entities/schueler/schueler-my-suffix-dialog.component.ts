import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { SchuelerMySuffix } from './schueler-my-suffix.model';
import { SchuelerMySuffixPopupService } from './schueler-my-suffix-popup.service';
import { SchuelerMySuffixService } from './schueler-my-suffix.service';
import { KlasseMySuffix, KlasseMySuffixService } from '../klasse';
import { AnschriftMySuffix, AnschriftMySuffixService } from '../anschrift';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-schueler-my-suffix-dialog',
    templateUrl: './schueler-my-suffix-dialog.component.html'
})
export class SchuelerMySuffixDialogComponent implements OnInit {

    schueler: SchuelerMySuffix;
    authorities: any[];
    isSaving: boolean;

    klasses: KlasseMySuffix[];

    anschrifts: AnschriftMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private schuelerService: SchuelerMySuffixService,
        private klasseService: KlasseMySuffixService,
        private anschriftService: AnschriftMySuffixService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.klasseService.query()
            .subscribe((res: ResponseWrapper) => { this.klasses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.anschriftService.query()
            .subscribe((res: ResponseWrapper) => { this.anschrifts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.schueler.id !== undefined) {
            this.subscribeToSaveResponse(
                this.schuelerService.update(this.schueler));
        } else {
            this.subscribeToSaveResponse(
                this.schuelerService.create(this.schueler));
        }
    }

    private subscribeToSaveResponse(result: Observable<SchuelerMySuffix>) {
        result.subscribe((res: SchuelerMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SchuelerMySuffix) {
        this.eventManager.broadcast({ name: 'schuelerListModification', content: 'OK'});
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

    trackAnschriftById(index: number, item: AnschriftMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-schueler-my-suffix-popup',
    template: ''
})
export class SchuelerMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schuelerPopupService: SchuelerMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.schuelerPopupService
                    .open(SchuelerMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.schuelerPopupService
                    .open(SchuelerMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
