import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ZeugnisMySuffix } from './zeugnis-my-suffix.model';
import { ZeugnisMySuffixPopupService } from './zeugnis-my-suffix-popup.service';
import { ZeugnisMySuffixService } from './zeugnis-my-suffix.service';

@Component({
    selector: 'jhi-zeugnis-my-suffix-delete-dialog',
    templateUrl: './zeugnis-my-suffix-delete-dialog.component.html'
})
export class ZeugnisMySuffixDeleteDialogComponent {

    zeugnis: ZeugnisMySuffix;

    constructor(
        private zeugnisService: ZeugnisMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.zeugnisService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'zeugnisListModification',
                content: 'Deleted an zeugnis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-zeugnis-my-suffix-delete-popup',
    template: ''
})
export class ZeugnisMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private zeugnisPopupService: ZeugnisMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.zeugnisPopupService
                .open(ZeugnisMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
