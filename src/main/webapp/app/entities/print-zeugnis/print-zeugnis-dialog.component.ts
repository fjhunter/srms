import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PrintZeugnis } from './print-zeugnis.model';
import { PrintZeugnisPopupService } from './print-zeugnis-popup.service';
import { PrintZeugnisService } from './print-zeugnis.service';

@Component({
    selector: 'jhi-print-zeugnis-dialog',
    templateUrl: './print-zeugnis-dialog.component.html'
})
export class PrintZeugnisDialogComponent implements OnInit {

    printZeugnis: PrintZeugnis;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private printZeugnisService: PrintZeugnisService,
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
        if (this.printZeugnis.id !== undefined) {
            this.subscribeToSaveResponse(
                this.printZeugnisService.update(this.printZeugnis));
        } else {
            this.subscribeToSaveResponse(
                this.printZeugnisService.create(this.printZeugnis));
        }
    }

    private subscribeToSaveResponse(result: Observable<PrintZeugnis>) {
        result.subscribe((res: PrintZeugnis) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PrintZeugnis) {
        this.eventManager.broadcast({ name: 'printZeugnisListModification', content: 'OK'});
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
    selector: 'jhi-print-zeugnis-popup',
    template: ''
})
export class PrintZeugnisPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private printZeugnisPopupService: PrintZeugnisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.printZeugnisPopupService
                    .open(PrintZeugnisDialogComponent, params['id']);
            } else {
                this.modalRef = this.printZeugnisPopupService
                    .open(PrintZeugnisDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
