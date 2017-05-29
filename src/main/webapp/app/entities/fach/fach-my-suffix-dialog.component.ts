import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { FachMySuffix } from './fach-my-suffix.model';
import { FachMySuffixPopupService } from './fach-my-suffix-popup.service';
import { FachMySuffixService } from './fach-my-suffix.service';

@Component({
    selector: 'jhi-fach-my-suffix-dialog',
    templateUrl: './fach-my-suffix-dialog.component.html'
})
export class FachMySuffixDialogComponent implements OnInit {

    fach: FachMySuffix;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private fachService: FachMySuffixService,
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
        if (this.fach.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fachService.update(this.fach));
        } else {
            this.subscribeToSaveResponse(
                this.fachService.create(this.fach));
        }
    }

    private subscribeToSaveResponse(result: Observable<FachMySuffix>) {
        result.subscribe((res: FachMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FachMySuffix) {
        this.eventManager.broadcast({ name: 'fachListModification', content: 'OK'});
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
    selector: 'jhi-fach-my-suffix-popup',
    template: ''
})
export class FachMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fachPopupService: FachMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.fachPopupService
                    .open(FachMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.fachPopupService
                    .open(FachMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
