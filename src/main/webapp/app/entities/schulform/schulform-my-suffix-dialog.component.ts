import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { SchulformMySuffix } from './schulform-my-suffix.model';
import { SchulformMySuffixPopupService } from './schulform-my-suffix-popup.service';
import { SchulformMySuffixService } from './schulform-my-suffix.service';

@Component({
    selector: 'jhi-schulform-my-suffix-dialog',
    templateUrl: './schulform-my-suffix-dialog.component.html'
})
export class SchulformMySuffixDialogComponent implements OnInit {

    schulform: SchulformMySuffix;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private schulformService: SchulformMySuffixService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.schulform.id !== undefined) {
            this.subscribeToSaveResponse(
                this.schulformService.update(this.schulform));
        } else {
            this.subscribeToSaveResponse(
                this.schulformService.create(this.schulform));
        }
    }

    private subscribeToSaveResponse(result: Observable<SchulformMySuffix>) {
        result.subscribe((res: SchulformMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SchulformMySuffix) {
        this.eventManager.broadcast({ name: 'schulformListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-schulform-my-suffix-popup',
    template: ''
})
export class SchulformMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schulformPopupService: SchulformMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.schulformPopupService
                    .open(SchulformMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.schulformPopupService
                    .open(SchulformMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
