import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { LehrerMySuffix } from './lehrer-my-suffix.model';
import { LehrerMySuffixPopupService } from './lehrer-my-suffix-popup.service';
import { LehrerMySuffixService } from './lehrer-my-suffix.service';

@Component({
    selector: 'jhi-lehrer-my-suffix-delete-dialog',
    templateUrl: './lehrer-my-suffix-delete-dialog.component.html'
})
export class LehrerMySuffixDeleteDialogComponent {

    lehrer: LehrerMySuffix;

    constructor(
        private lehrerService: LehrerMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lehrerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lehrerListModification',
                content: 'Deleted an lehrer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lehrer-my-suffix-delete-popup',
    template: ''
})
export class LehrerMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lehrerPopupService: LehrerMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.lehrerPopupService
                .open(LehrerMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
