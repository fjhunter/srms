import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { FachMySuffix } from './fach-my-suffix.model';
import { FachMySuffixService } from './fach-my-suffix.service';

@Component({
    selector: 'jhi-fach-my-suffix-detail',
    templateUrl: './fach-my-suffix-detail.component.html'
})
export class FachMySuffixDetailComponent implements OnInit, OnDestroy {

    fach: FachMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private fachService: FachMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFaches();
    }

    load(id) {
        this.fachService.find(id).subscribe((fach) => {
            this.fach = fach;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFaches() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fachListModification',
            (response) => this.load(this.fach.id)
        );
    }
}
