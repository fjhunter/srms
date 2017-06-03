import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PrintZeugnis } from './print-zeugnis.model';
import { PrintZeugnisPopupService } from './print-zeugnis-popup.service';
import { PrintZeugnisService } from './print-zeugnis.service';

@Component({
    selector: 'jhi-print-zeugnis-delete-dialog',
    templateUrl: './print-zeugnis-delete-dialog.component.html'
})
export class PrintZeugnisDeleteDialogComponent {

    printZeugnis: PrintZeugnis;

    constructor(
        private printZeugnisService: PrintZeugnisService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.printZeugnisService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'printZeugnisListModification',
                content: 'Deleted an printZeugnis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-print-zeugnis-delete-popup',
    template: ''
})
export class PrintZeugnisDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private printZeugnisPopupService: PrintZeugnisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.printZeugnisPopupService
                .open(PrintZeugnisDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
