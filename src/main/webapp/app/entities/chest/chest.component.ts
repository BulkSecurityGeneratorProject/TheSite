import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IChest } from 'app/shared/model/chest.model';
import { Principal } from 'app/core';
import { ChestService } from './chest.service';

@Component({
    selector: 'jhi-chest',
    templateUrl: './chest.component.html'
})
export class ChestComponent implements OnInit, OnDestroy {
    chests: IChest[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private chestService: ChestService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.chestService.query().subscribe(
            (res: HttpResponse<IChest[]>) => {
                this.chests = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInChests();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IChest) {
        return item.id;
    }

    registerChangeInChests() {
        this.eventSubscriber = this.eventManager.subscribe('chestListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
