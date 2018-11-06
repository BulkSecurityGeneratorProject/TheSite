import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IChestItem } from 'app/shared/model/chest-item.model';
import { Principal } from 'app/core';
import { ChestItemService } from './chest-item.service';

@Component({
    selector: 'jhi-chest-item',
    templateUrl: './chest-item.component.html'
})
export class ChestItemComponent implements OnInit, OnDestroy {
    chestItems: IChestItem[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private chestItemService: ChestItemService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.chestItemService.query().subscribe(
            (res: HttpResponse<IChestItem[]>) => {
                this.chestItems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInChestItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IChestItem) {
        return item.id;
    }

    registerChangeInChestItems() {
        this.eventSubscriber = this.eventManager.subscribe('chestItemListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
