import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { KlasseMySuffix } from './klasse-my-suffix.model';
import { KlasseMySuffixPopupService } from './klasse-my-suffix-popup.service';
import { KlasseMySuffixService } from './klasse-my-suffix.service';
import { LehrerMySuffix, LehrerMySuffixService } from '../lehrer';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-klasse-my-suffix-dialog',
    templateUrl: './klasse-my-suffix-dialog.component.html'
})
export class KlasseMySuffixDialogComponent implements OnInit {

    klasse: KlasseMySuffix;
    authorities: any[];
    isSaving: boolean;

    lehrers: LehrerMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private klasseService: KlasseMySuffixService,
        private lehrerService: LehrerMySuffixService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.lehrerService.query()
            .subscribe((res: ResponseWrapper) => { this.lehrers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.klasse.id !== undefined) {
            this.subscribeToSaveResponse(
                this.klasseService.update(this.klasse));
        } else {
            this.subscribeToSaveResponse(
                this.klasseService.create(this.klasse));
        }
    }

    private subscribeToSaveResponse(result: Observable<KlasseMySuffix>) {
        result.subscribe((res: KlasseMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: KlasseMySuffix) {
        this.eventManager.broadcast({ name: 'klasseListModification', content: 'OK'});
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

    trackLehrerById(index: number, item: LehrerMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-klasse-my-suffix-popup',
    template: ''
})
export class KlasseMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private klassePopupService: KlasseMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.klassePopupService
                    .open(KlasseMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.klassePopupService
                    .open(KlasseMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
