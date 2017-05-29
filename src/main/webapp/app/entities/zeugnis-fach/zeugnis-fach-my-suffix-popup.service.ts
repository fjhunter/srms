import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ZeugnisFachMySuffix } from './zeugnis-fach-my-suffix.model';
import { ZeugnisFachMySuffixService } from './zeugnis-fach-my-suffix.service';
@Injectable()
export class ZeugnisFachMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private zeugnisFachService: ZeugnisFachMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.zeugnisFachService.find(id).subscribe((zeugnisFach) => {
                this.zeugnisFachModalRef(component, zeugnisFach);
            });
        } else {
            return this.zeugnisFachModalRef(component, new ZeugnisFachMySuffix());
        }
    }

    zeugnisFachModalRef(component: Component, zeugnisFach: ZeugnisFachMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.zeugnisFach = zeugnisFach;
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
