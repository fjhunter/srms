import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { FehlzeitenMySuffix } from './fehlzeiten-my-suffix.model';
import { FehlzeitenMySuffixPopupService } from './fehlzeiten-my-suffix-popup.service';
import { FehlzeitenMySuffixService } from './fehlzeiten-my-suffix.service';
import { SchuelerMySuffix, SchuelerMySuffixService } from '../schueler';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-fehlzeiten-my-suffix-dialog',
    templateUrl: './fehlzeiten-my-suffix-dialog.component.html'
})
export class FehlzeitenMySuffixDialogComponent implements OnInit {

    fehlzeiten: FehlzeitenMySuffix;
    authorities: any[];
    isSaving: boolean;

    schuelers: SchuelerMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private fehlzeitenService: FehlzeitenMySuffixService,
        private schuelerService: SchuelerMySuffixService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.schuelerService.query()
            .subscribe((res: ResponseWrapper) => { this.schuelers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fehlzeiten.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fehlzeitenService.update(this.fehlzeiten));
        } else {
            this.subscribeToSaveResponse(
                this.fehlzeitenService.create(this.fehlzeiten));
        }
    }

    private subscribeToSaveResponse(result: Observable<FehlzeitenMySuffix>) {
        result.subscribe((res: FehlzeitenMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FehlzeitenMySuffix) {
        this.eventManager.broadcast({ name: 'fehlzeitenListModification', content: 'OK'});
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

    trackSchuelerById(index: number, item: SchuelerMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-fehlzeiten-my-suffix-popup',
    template: ''
})
export class FehlzeitenMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fehlzeitenPopupService: FehlzeitenMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.fehlzeitenPopupService
                    .open(FehlzeitenMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.fehlzeitenPopupService
                    .open(FehlzeitenMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
