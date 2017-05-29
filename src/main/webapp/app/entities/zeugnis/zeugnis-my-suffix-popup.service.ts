import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ZeugnisMySuffix } from './zeugnis-my-suffix.model';
import { ZeugnisMySuffixService } from './zeugnis-my-suffix.service';
@Injectable()
export class ZeugnisMySuffixPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private zeugnisService: ZeugnisMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.zeugnisService.find(id).subscribe((zeugnis) => {
                zeugnis.datum = this.datePipe
                    .transform(zeugnis.datum, 'yyyy-MM-ddThh:mm');
                this.zeugnisModalRef(component, zeugnis);
            });
        } else {
            return this.zeugnisModalRef(component, new ZeugnisMySuffix());
        }
    }

    zeugnisModalRef(component: Component, zeugnis: ZeugnisMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.zeugnis = zeugnis;
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
