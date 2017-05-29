import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { SchuelerMySuffix } from './schueler-my-suffix.model';
import { SchuelerMySuffixPopupService } from './schueler-my-suffix-popup.service';
import { SchuelerMySuffixService } from './schueler-my-suffix.service';

@Component({
    selector: 'jhi-schueler-my-suffix-delete-dialog',
    templateUrl: './schueler-my-suffix-delete-dialog.component.html'
})
export class SchuelerMySuffixDeleteDialogComponent {

    schueler: SchuelerMySuffix;

    constructor(
        private schuelerService: SchuelerMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.schuelerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'schuelerListModification',
                content: 'Deleted an schueler'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-schueler-my-suffix-delete-popup',
    template: ''
})
export class SchuelerMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schuelerPopupService: SchuelerMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.schuelerPopupService
                .open(SchuelerMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
