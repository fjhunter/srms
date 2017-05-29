import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AnschriftMySuffix } from './anschrift-my-suffix.model';
import { AnschriftMySuffixService } from './anschrift-my-suffix.service';
@Injectable()
export class AnschriftMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private anschriftService: AnschriftMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.anschriftService.find(id).subscribe((anschrift) => {
                this.anschriftModalRef(component, anschrift);
            });
        } else {
            return this.anschriftModalRef(component, new AnschriftMySuffix());
        }
    }

    anschriftModalRef(component: Component, anschrift: AnschriftMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.anschrift = anschrift;
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
