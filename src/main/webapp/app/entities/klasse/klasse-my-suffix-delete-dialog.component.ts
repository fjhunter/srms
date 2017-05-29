import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { KlasseMySuffix } from './klasse-my-suffix.model';
import { KlasseMySuffixPopupService } from './klasse-my-suffix-popup.service';
import { KlasseMySuffixService } from './klasse-my-suffix.service';

@Component({
    selector: 'jhi-klasse-my-suffix-delete-dialog',
    templateUrl: './klasse-my-suffix-delete-dialog.component.html'
})
export class KlasseMySuffixDeleteDialogComponent {

    klasse: KlasseMySuffix;

    constructor(
        private klasseService: KlasseMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.klasseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'klasseListModification',
                content: 'Deleted an klasse'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-klasse-my-suffix-delete-popup',
    template: ''
})
export class KlasseMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private klassePopupService: KlasseMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.klassePopupService
                .open(KlasseMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
