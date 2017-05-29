import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { SchulformMySuffix } from './schulform-my-suffix.model';
import { SchulformMySuffixService } from './schulform-my-suffix.service';

@Component({
    selector: 'jhi-schulform-my-suffix-detail',
    templateUrl: './schulform-my-suffix-detail.component.html'
})
export class SchulformMySuffixDetailComponent implements OnInit, OnDestroy {

    schulform: SchulformMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private schulformService: SchulformMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSchulforms();
    }

    load(id) {
        this.schulformService.find(id).subscribe((schulform) => {
            this.schulform = schulform;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSchulforms() {
        this.eventSubscriber = this.eventManager.subscribe(
            'schulformListModification',
            (response) => this.load(this.schulform.id)
        );
    }
}
