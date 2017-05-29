import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { KlasseFachMySuffix } from './klasse-fach-my-suffix.model';
import { KlasseFachMySuffixPopupService } from './klasse-fach-my-suffix-popup.service';
import { KlasseFachMySuffixService } from './klasse-fach-my-suffix.service';

@Component({
    selector: 'jhi-klasse-fach-my-suffix-delete-dialog',
    templateUrl: './klasse-fach-my-suffix-delete-dialog.component.html'
})
export class KlasseFachMySuffixDeleteDialogComponent {

    klasseFach: KlasseFachMySuffix;

    constructor(
        private klasseFachService: KlasseFachMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.klasseFachService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'klasseFachListModification',
                content: 'Deleted an klasseFach'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-klasse-fach-my-suffix-delete-popup',
    template: ''
})
export class KlasseFachMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private klasseFachPopupService: KlasseFachMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.klasseFachPopupService
                .open(KlasseFachMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
