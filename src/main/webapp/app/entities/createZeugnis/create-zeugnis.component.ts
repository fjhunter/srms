
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-fach-my-suffix',
    templateUrl: './fach-my-suffix.component.html'
})
export class CreateZeugnisComponent implements OnInit, OnDestroy {

    constructor(
    ) {
    }

    ngOnInit() {
    }

    ngOnDestroy() {
    }

}
