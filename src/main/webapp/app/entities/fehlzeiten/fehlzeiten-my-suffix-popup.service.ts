import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { FehlzeitenMySuffix } from './fehlzeiten-my-suffix.model';
import { FehlzeitenMySuffixService } from './fehlzeiten-my-suffix.service';
@Injectable()
export class FehlzeitenMySuffixPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private fehlzeitenService: FehlzeitenMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.fehlzeitenService.find(id).subscribe((fehlzeiten) => {
                fehlzeiten.datum = this.datePipe
                    .transform(fehlzeiten.datum, 'yyyy-MM-ddThh:mm');
                this.fehlzeitenModalRef(component, fehlzeiten);
            });
        } else {
            return this.fehlzeitenModalRef(component, new FehlzeitenMySuffix());
        }
    }

    fehlzeitenModalRef(component: Component, fehlzeiten: FehlzeitenMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fehlzeiten = fehlzeiten;
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
