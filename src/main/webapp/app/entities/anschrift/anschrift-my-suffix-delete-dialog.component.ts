import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { AnschriftMySuffix } from './anschrift-my-suffix.model';
import { AnschriftMySuffixPopupService } from './anschrift-my-suffix-popup.service';
import { AnschriftMySuffixService } from './anschrift-my-suffix.service';

@Component({
    selector: 'jhi-anschrift-my-suffix-delete-dialog',
    templateUrl: './anschrift-my-suffix-delete-dialog.component.html'
})
export class AnschriftMySuffixDeleteDialogComponent {

    anschrift: AnschriftMySuffix;

    constructor(
        private anschriftService: AnschriftMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.anschriftService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'anschriftListModification',
                content: 'Deleted an anschrift'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-anschrift-my-suffix-delete-popup',
    template: ''
})
export class AnschriftMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anschriftPopupService: AnschriftMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.anschriftPopupService
                .open(AnschriftMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
