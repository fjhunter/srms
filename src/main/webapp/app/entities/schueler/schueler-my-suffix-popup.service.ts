import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SchuelerMySuffix } from './schueler-my-suffix.model';
import { SchuelerMySuffixService } from './schueler-my-suffix.service';
@Injectable()
export class SchuelerMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private schuelerService: SchuelerMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.schuelerService.find(id).subscribe((schueler) => {
                this.schuelerModalRef(component, schueler);
            });
        } else {
            return this.schuelerModalRef(component, new SchuelerMySuffix());
        }
    }

    schuelerModalRef(component: Component, schueler: SchuelerMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.schueler = schueler;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
