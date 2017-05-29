import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { KlasseFachMySuffix } from './klasse-fach-my-suffix.model';
import { KlasseFachMySuffixService } from './klasse-fach-my-suffix.service';
@Injectable()
export class KlasseFachMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private klasseFachService: KlasseFachMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.klasseFachService.find(id).subscribe((klasseFach) => {
                this.klasseFachModalRef(component, klasseFach);
            });
        } else {
            return this.klasseFachModalRef(component, new KlasseFachMySuffix());
        }
    }

    klasseFachModalRef(component: Component, klasseFach: KlasseFachMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.klasseFach = klasseFach;
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
