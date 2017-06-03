import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { CreateZeugnis } from './create-zeugnis.model';
import { CreateZeugnisService } from './create-zeugnis.service';

@Component({
    selector: 'jhi-create-zeugnis-detail',
    templateUrl: './create-zeugnis-detail.component.html'
})
export class CreateZeugnisDetailComponent implements OnInit, OnDestroy {

    createZeugnis: CreateZeugnis;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private createZeugnisService: CreateZeugnisService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCreateZeugnis();
    }

    load(id) {
        this.createZeugnisService.find(id).subscribe((createZeugnis) => {
            this.createZeugnis = createZeugnis;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCreateZeugnis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'createZeugnisListModification',
            (response) => this.load(this.createZeugnis.id)
        );
    }
}
