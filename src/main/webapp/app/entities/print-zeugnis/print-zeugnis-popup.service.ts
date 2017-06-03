import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PrintZeugnis } from './print-zeugnis.model';
import { PrintZeugnisService } from './print-zeugnis.service';
@Injectable()
export class PrintZeugnisPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private printZeugnisService: PrintZeugnisService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.printZeugnisService.find(id).subscribe((printZeugnis) => {
                this.printZeugnisModalRef(component, printZeugnis);
            });
        } else {
            return this.printZeugnisModalRef(component, new PrintZeugnis());
        }
    }

    printZeugnisModalRef(component: Component, printZeugnis: PrintZeugnis): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.printZeugnis = printZeugnis;
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
