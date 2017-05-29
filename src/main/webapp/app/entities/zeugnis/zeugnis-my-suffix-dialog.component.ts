import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ZeugnisMySuffix } from './zeugnis-my-suffix.model';
import { ZeugnisMySuffixPopupService } from './zeugnis-my-suffix-popup.service';
import { ZeugnisMySuffixService } from './zeugnis-my-suffix.service';
import { SchuelerMySuffix, SchuelerMySuffixService } from '../schueler';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-zeugnis-my-suffix-dialog',
    templateUrl: './zeugnis-my-suffix-dialog.component.html'
})
export class ZeugnisMySuffixDialogComponent implements OnInit {

    zeugnis: ZeugnisMySuffix;
    authorities: any[];
    isSaving: boolean;

    schuelers: SchuelerMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private zeugnisService: ZeugnisMySuffixService,
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
        if (this.zeugnis.id !== undefined) {
            this.subscribeToSaveResponse(
                this.zeugnisService.update(this.zeugnis));
        } else {
            this.subscribeToSaveResponse(
                this.zeugnisService.create(this.zeugnis));
        }
    }

    private subscribeToSaveResponse(result: Observable<ZeugnisMySuffix>) {
        result.subscribe((res: ZeugnisMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ZeugnisMySuffix) {
        this.eventManager.broadcast({ name: 'zeugnisListModification', content: 'OK'});
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
    selector: 'jhi-zeugnis-my-suffix-popup',
    template: ''
})
export class ZeugnisMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private zeugnisPopupService: ZeugnisMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.zeugnisPopupService
                    .open(ZeugnisMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.zeugnisPopupService
                    .open(ZeugnisMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
