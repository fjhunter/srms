import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LehrerMySuffix } from './lehrer-my-suffix.model';
import { LehrerMySuffixService } from './lehrer-my-suffix.service';
@Injectable()
export class LehrerMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private lehrerService: LehrerMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.lehrerService.find(id).subscribe((lehrer) => {
                this.lehrerModalRef(component, lehrer);
            });
        } else {
            return this.lehrerModalRef(component, new LehrerMySuffix());
        }
    }

    lehrerModalRef(component: Component, lehrer: LehrerMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lehrer = lehrer;
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
