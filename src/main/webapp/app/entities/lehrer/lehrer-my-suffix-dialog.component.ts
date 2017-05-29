import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { LehrerMySuffix } from './lehrer-my-suffix.model';
import { LehrerMySuffixPopupService } from './lehrer-my-suffix-popup.service';
import { LehrerMySuffixService } from './lehrer-my-suffix.service';

@Component({
    selector: 'jhi-lehrer-my-suffix-dialog',
    templateUrl: './lehrer-my-suffix-dialog.component.html'
})
export class LehrerMySuffixDialogComponent implements OnInit {

    lehrer: LehrerMySuffix;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private lehrerService: LehrerMySuffixService,
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
        if (this.lehrer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lehrerService.update(this.lehrer));
        } else {
            this.subscribeToSaveResponse(
                this.lehrerService.create(this.lehrer));
        }
    }

    private subscribeToSaveResponse(result: Observable<LehrerMySuffix>) {
        result.subscribe((res: LehrerMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: LehrerMySuffix) {
        this.eventManager.broadcast({ name: 'lehrerListModification', content: 'OK'});
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
    selector: 'jhi-lehrer-my-suffix-popup',
    template: ''
})
export class LehrerMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lehrerPopupService: LehrerMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.lehrerPopupService
                    .open(LehrerMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.lehrerPopupService
                    .open(LehrerMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
