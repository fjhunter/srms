import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { CreateZeugnis } from './create-zeugnis.model';
import { CreateZeugnisPopupService } from './create-zeugnis-popup.service';
import { CreateZeugnisService } from './create-zeugnis.service';

@Component({
    selector: 'jhi-create-zeugnis-dialog',
    templateUrl: './create-zeugnis-dialog.component.html'
})
export class CreateZeugnisDialogComponent implements OnInit {

    createZeugnis: CreateZeugnis;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private createZeugnisService: CreateZeugnisService,
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
        if (this.createZeugnis.id !== undefined) {
            this.subscribeToSaveResponse(
                this.createZeugnisService.update(this.createZeugnis));
        } else {
            this.subscribeToSaveResponse(
                this.createZeugnisService.create(this.createZeugnis));
        }
    }

    private subscribeToSaveResponse(result: Observable<CreateZeugnis>) {
        result.subscribe((res: CreateZeugnis) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CreateZeugnis) {
        this.eventManager.broadcast({ name: 'createZeugnisListModification', content: 'OK'});
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
    selector: 'jhi-create-zeugnis-popup',
    template: ''
})
export class CreateZeugnisPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private createZeugnisPopupService: CreateZeugnisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.createZeugnisPopupService
                    .open(CreateZeugnisDialogComponent, params['id']);
            } else {
                this.modalRef = this.createZeugnisPopupService
                    .open(CreateZeugnisDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
