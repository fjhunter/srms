import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ZeugnisFachMySuffix } from './zeugnis-fach-my-suffix.model';
import { ZeugnisFachMySuffixPopupService } from './zeugnis-fach-my-suffix-popup.service';
import { ZeugnisFachMySuffixService } from './zeugnis-fach-my-suffix.service';

@Component({
    selector: 'jhi-zeugnis-fach-my-suffix-delete-dialog',
    templateUrl: './zeugnis-fach-my-suffix-delete-dialog.component.html'
})
export class ZeugnisFachMySuffixDeleteDialogComponent {

    zeugnisFach: ZeugnisFachMySuffix;

    constructor(
        private zeugnisFachService: ZeugnisFachMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.zeugnisFachService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'zeugnisFachListModification',
                content: 'Deleted an zeugnisFach'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-zeugnis-fach-my-suffix-delete-popup',
    template: ''
})
export class ZeugnisFachMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private zeugnisFachPopupService: ZeugnisFachMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.zeugnisFachPopupService
                .open(ZeugnisFachMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
