import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { LehrerMySuffix } from './lehrer-my-suffix.model';
import { LehrerMySuffixService } from './lehrer-my-suffix.service';

@Component({
    selector: 'jhi-lehrer-my-suffix-detail',
    templateUrl: './lehrer-my-suffix-detail.component.html'
})
export class LehrerMySuffixDetailComponent implements OnInit, OnDestroy {

    lehrer: LehrerMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private lehrerService: LehrerMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLehrers();
    }

    load(id) {
        this.lehrerService.find(id).subscribe((lehrer) => {
            this.lehrer = lehrer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLehrers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lehrerListModification',
            (response) => this.load(this.lehrer.id)
        );
    }
}
