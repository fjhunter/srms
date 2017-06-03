import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PrintZeugnis } from './print-zeugnis.model';
import { PrintZeugnisService } from './print-zeugnis.service';

@Component({
    selector: 'jhi-print-zeugnis-detail',
    templateUrl: './print-zeugnis-detail.component.html'
})
export class PrintZeugnisDetailComponent implements OnInit, OnDestroy {

    printZeugnis: PrintZeugnis;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private printZeugnisService: PrintZeugnisService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrintZeugnis();
    }

    load(id) {
        this.printZeugnisService.find(id).subscribe((printZeugnis) => {
            this.printZeugnis = printZeugnis;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrintZeugnis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'printZeugnisListModification',
            (response) => this.load(this.printZeugnis.id)
        );
    }
}
