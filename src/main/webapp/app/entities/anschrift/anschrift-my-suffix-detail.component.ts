import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { AnschriftMySuffix } from './anschrift-my-suffix.model';
import { AnschriftMySuffixService } from './anschrift-my-suffix.service';

@Component({
    selector: 'jhi-anschrift-my-suffix-detail',
    templateUrl: './anschrift-my-suffix-detail.component.html'
})
export class AnschriftMySuffixDetailComponent implements OnInit, OnDestroy {

    anschrift: AnschriftMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private anschriftService: AnschriftMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAnschrifts();
    }

    load(id) {
        this.anschriftService.find(id).subscribe((anschrift) => {
            this.anschrift = anschrift;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAnschrifts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'anschriftListModification',
            (response) => this.load(this.anschrift.id)
        );
    }
}
