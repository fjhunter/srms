import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { FachMySuffix } from './fach-my-suffix.model';
import { FachMySuffixPopupService } from './fach-my-suffix-popup.service';
import { FachMySuffixService } from './fach-my-suffix.service';

@Component({
    selector: 'jhi-fach-my-suffix-delete-dialog',
    templateUrl: './fach-my-suffix-delete-dialog.component.html'
})
export class FachMySuffixDeleteDialogComponent {

    fach: FachMySuffix;

    constructor(
        private fachService: FachMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fachService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fachListModification',
                content: 'Deleted an fach'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fach-my-suffix-delete-popup',
    template: ''
})
export class FachMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fachPopupService: FachMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.fachPopupService
                .open(FachMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
