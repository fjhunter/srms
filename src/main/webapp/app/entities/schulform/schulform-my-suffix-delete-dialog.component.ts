import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { SchulformMySuffix } from './schulform-my-suffix.model';
import { SchulformMySuffixPopupService } from './schulform-my-suffix-popup.service';
import { SchulformMySuffixService } from './schulform-my-suffix.service';

@Component({
    selector: 'jhi-schulform-my-suffix-delete-dialog',
    templateUrl: './schulform-my-suffix-delete-dialog.component.html'
})
export class SchulformMySuffixDeleteDialogComponent {

    schulform: SchulformMySuffix;

    constructor(
        private schulformService: SchulformMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.schulformService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'schulformListModification',
                content: 'Deleted an schulform'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-schulform-my-suffix-delete-popup',
    template: ''
})
export class SchulformMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schulformPopupService: SchulformMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.schulformPopupService
                .open(SchulformMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
