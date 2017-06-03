import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { CreateZeugnis } from './create-zeugnis.model';
import { CreateZeugnisPopupService } from './create-zeugnis-popup.service';
import { CreateZeugnisService } from './create-zeugnis.service';

@Component({
    selector: 'jhi-create-zeugnis-delete-dialog',
    templateUrl: './create-zeugnis-delete-dialog.component.html'
})
export class CreateZeugnisDeleteDialogComponent {

    createZeugnis: CreateZeugnis;

    constructor(
        private createZeugnisService: CreateZeugnisService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.createZeugnisService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'createZeugnisListModification',
                content: 'Deleted an createZeugnis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-create-zeugnis-delete-popup',
    template: ''
})
export class CreateZeugnisDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private createZeugnisPopupService: CreateZeugnisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.createZeugnisPopupService
                .open(CreateZeugnisDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
