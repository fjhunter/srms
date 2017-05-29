import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { KlasseMySuffix } from './klasse-my-suffix.model';
import { KlasseMySuffixService } from './klasse-my-suffix.service';
@Injectable()
export class KlasseMySuffixPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private klasseService: KlasseMySuffixService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.klasseService.find(id).subscribe((klasse) => {
                this.klasseModalRef(component, klasse);
            });
        } else {
            return this.klasseModalRef(component, new KlasseMySuffix());
        }
    }

    klasseModalRef(component: Component, klasse: KlasseMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.klasse = klasse;
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
