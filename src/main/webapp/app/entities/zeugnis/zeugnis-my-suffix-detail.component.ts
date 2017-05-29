import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ZeugnisMySuffix } from './zeugnis-my-suffix.model';
import { ZeugnisMySuffixService } from './zeugnis-my-suffix.service';

@Component({
    selector: 'jhi-zeugnis-my-suffix-detail',
    templateUrl: './zeugnis-my-suffix-detail.component.html'
})
export class ZeugnisMySuffixDetailComponent implements OnInit, OnDestroy {

    zeugnis: ZeugnisMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private zeugnisService: ZeugnisMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInZeugnis();
    }

    load(id) {
        this.zeugnisService.find(id).subscribe((zeugnis) => {
            this.zeugnis = zeugnis;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInZeugnis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'zeugnisListModification',
            (response) => this.load(this.zeugnis.id)
        );
    }
}
