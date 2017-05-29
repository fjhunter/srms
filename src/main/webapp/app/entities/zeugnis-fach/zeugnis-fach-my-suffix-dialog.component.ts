import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ZeugnisFachMySuffix } from './zeugnis-fach-my-suffix.model';
import { ZeugnisFachMySuffixPopupService } from './zeugnis-fach-my-suffix-popup.service';
import { ZeugnisFachMySuffixService } from './zeugnis-fach-my-suffix.service';
import { ZeugnisMySuffix, ZeugnisMySuffixService } from '../zeugnis';
import { FachMySuffix, FachMySuffixService } from '../fach';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-zeugnis-fach-my-suffix-dialog',
    templateUrl: './zeugnis-fach-my-suffix-dialog.component.html'
})
export class ZeugnisFachMySuffixDialogComponent implements OnInit {

    zeugnisFach: ZeugnisFachMySuffix;
    authorities: any[];
    isSaving: boolean;

    zeugnis: ZeugnisMySuffix[];

    faches: FachMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private zeugnisFachService: ZeugnisFachMySuffixService,
        private zeugnisService: ZeugnisMySuffixService,
        private fachService: FachMySuffixService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.zeugnisService.query()
            .subscribe((res: ResponseWrapper) => { this.zeugnis = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.fachService.query()
            .subscribe((res: ResponseWrapper) => { this.faches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.zeugnisFach.id !== undefined) {
            this.subscribeToSaveResponse(
                this.zeugnisFachService.update(this.zeugnisFach));
        } else {
            this.subscribeToSaveResponse(
                this.zeugnisFachService.create(this.zeugnisFach));
        }
    }

    private subscribeToSaveResponse(result: Observable<ZeugnisFachMySuffix>) {
        result.subscribe((res: ZeugnisFachMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ZeugnisFachMySuffix) {
        this.eventManager.broadcast({ name: 'zeugnisFachListModification', content: 'OK'});
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

    trackZeugnisById(index: number, item: ZeugnisMySuffix) {
        return item.id;
    }

    trackFachById(index: number, item: FachMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-zeugnis-fach-my-suffix-popup',
    template: ''
})
export class ZeugnisFachMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private zeugnisFachPopupService: ZeugnisFachMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.zeugnisFachPopupService
                    .open(ZeugnisFachMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.zeugnisFachPopupService
                    .open(ZeugnisFachMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
