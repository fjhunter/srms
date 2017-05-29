import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ZeugnisFachMySuffix } from './zeugnis-fach-my-suffix.model';
import { ZeugnisFachMySuffixService } from './zeugnis-fach-my-suffix.service';

@Component({
    selector: 'jhi-zeugnis-fach-my-suffix-detail',
    templateUrl: './zeugnis-fach-my-suffix-detail.component.html'
})
export class ZeugnisFachMySuffixDetailComponent implements OnInit, OnDestroy {

    zeugnisFach: ZeugnisFachMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private zeugnisFachService: ZeugnisFachMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInZeugnisFaches();
    }

    load(id) {
        this.zeugnisFachService.find(id).subscribe((zeugnisFach) => {
            this.zeugnisFach = zeugnisFach;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInZeugnisFaches() {
        this.eventSubscriber = this.eventManager.subscribe(
            'zeugnisFachListModification',
            (response) => this.load(this.zeugnisFach.id)
        );
    }
}
