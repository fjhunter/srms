import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SchulformMySuffix } from './schulform-my-suffix.model';
import { SchulformMySuffixService } from './schulform-my-suffix.service';
@Injectable()
export class SchulformMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private schulformService: SchulformMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.schulformService.find(id).subscribe((schulform) => {
                this.schulformModalRef(component, schulform);
            });
        } else {
            return this.schulformModalRef(component, new SchulformMySuffix());
        }
    }

    schulformModalRef(component: Component, schulform: SchulformMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.schulform = schulform;
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
