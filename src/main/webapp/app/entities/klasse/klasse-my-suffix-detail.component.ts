import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { KlasseMySuffix } from './klasse-my-suffix.model';
import { KlasseMySuffixService } from './klasse-my-suffix.service';

@Component({
    selector: 'jhi-klasse-my-suffix-detail',
    templateUrl: './klasse-my-suffix-detail.component.html'
})
export class KlasseMySuffixDetailComponent implements OnInit, OnDestroy {

    klasse: KlasseMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private klasseService: KlasseMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKlasses();
    }

    load(id) {
        this.klasseService.find(id).subscribe((klasse) => {
            this.klasse = klasse;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKlasses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'klasseListModification',
            (response) => this.load(this.klasse.id)
        );
    }
}
