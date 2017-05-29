import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { FehlzeitenMySuffix } from './fehlzeiten-my-suffix.model';
import { FehlzeitenMySuffixService } from './fehlzeiten-my-suffix.service';

@Component({
    selector: 'jhi-fehlzeiten-my-suffix-detail',
    templateUrl: './fehlzeiten-my-suffix-detail.component.html'
})
export class FehlzeitenMySuffixDetailComponent implements OnInit, OnDestroy {

    fehlzeiten: FehlzeitenMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private fehlzeitenService: FehlzeitenMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFehlzeitens();
    }

    load(id) {
        this.fehlzeitenService.find(id).subscribe((fehlzeiten) => {
            this.fehlzeiten = fehlzeiten;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFehlzeitens() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fehlzeitenListModification',
            (response) => this.load(this.fehlzeiten.id)
        );
    }
}
