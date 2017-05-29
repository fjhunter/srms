import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { KlasseFachMySuffix } from './klasse-fach-my-suffix.model';
import { KlasseFachMySuffixService } from './klasse-fach-my-suffix.service';

@Component({
    selector: 'jhi-klasse-fach-my-suffix-detail',
    templateUrl: './klasse-fach-my-suffix-detail.component.html'
})
export class KlasseFachMySuffixDetailComponent implements OnInit, OnDestroy {

    klasseFach: KlasseFachMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private klasseFachService: KlasseFachMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKlasseFaches();
    }

    load(id) {
        this.klasseFachService.find(id).subscribe((klasseFach) => {
            this.klasseFach = klasseFach;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKlasseFaches() {
        this.eventSubscriber = this.eventManager.subscribe(
            'klasseFachListModification',
            (response) => this.load(this.klasseFach.id)
        );
    }
}
