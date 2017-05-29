import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AnschriftMySuffix } from './anschrift-my-suffix.model';
import { AnschriftMySuffixPopupService } from './anschrift-my-suffix-popup.service';
import { AnschriftMySuffixService } from './anschrift-my-suffix.service';

@Component({
    selector: 'jhi-anschrift-my-suffix-dialog',
    templateUrl: './anschrift-my-suffix-dialog.component.html'
})
export class AnschriftMySuffixDialogComponent implements OnInit {

    anschrift: AnschriftMySuffix;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private anschriftService: AnschriftMySuffixService,
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
        if (this.anschrift.id !== undefined) {
            this.subscribeToSaveResponse(
                this.anschriftService.update(this.anschrift));
        } else {
            this.subscribeToSaveResponse(
                this.anschriftService.create(this.anschrift));
        }
    }

    private subscribeToSaveResponse(result: Observable<AnschriftMySuffix>) {
        result.subscribe((res: AnschriftMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AnschriftMySuffix) {
        this.eventManager.broadcast({ name: 'anschriftListModification', content: 'OK'});
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
    selector: 'jhi-anschrift-my-suffix-popup',
    template: ''
})
export class AnschriftMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anschriftPopupService: AnschriftMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.anschriftPopupService
                    .open(AnschriftMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.anschriftPopupService
                    .open(AnschriftMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
