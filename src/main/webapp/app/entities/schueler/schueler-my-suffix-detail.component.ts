import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { SchuelerMySuffix } from './schueler-my-suffix.model';
import { SchuelerMySuffixService } from './schueler-my-suffix.service';

@Component({
    selector: 'jhi-schueler-my-suffix-detail',
    templateUrl: './schueler-my-suffix-detail.component.html'
})
export class SchuelerMySuffixDetailComponent implements OnInit, OnDestroy {

    schueler: SchuelerMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private schuelerService: SchuelerMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSchuelers();
    }

    load(id) {
        this.schuelerService.find(id).subscribe((schueler) => {
            this.schueler = schueler;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSchuelers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'schuelerListModification',
            (response) => this.load(this.schueler.id)
        );
    }
}
