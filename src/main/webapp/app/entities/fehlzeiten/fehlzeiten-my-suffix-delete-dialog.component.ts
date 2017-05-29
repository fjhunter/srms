import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { FehlzeitenMySuffix } from './fehlzeiten-my-suffix.model';
import { FehlzeitenMySuffixPopupService } from './fehlzeiten-my-suffix-popup.service';
import { FehlzeitenMySuffixService } from './fehlzeiten-my-suffix.service';

@Component({
    selector: 'jhi-fehlzeiten-my-suffix-delete-dialog',
    templateUrl: './fehlzeiten-my-suffix-delete-dialog.component.html'
})
export class FehlzeitenMySuffixDeleteDialogComponent {

    fehlzeiten: FehlzeitenMySuffix;

    constructor(
        private fehlzeitenService: FehlzeitenMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fehlzeitenService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fehlzeitenListModification',
                content: 'Deleted an fehlzeiten'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fehlzeiten-my-suffix-delete-popup',
    template: ''
})
export class FehlzeitenMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fehlzeitenPopupService: FehlzeitenMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.fehlzeitenPopupService
                .open(FehlzeitenMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
