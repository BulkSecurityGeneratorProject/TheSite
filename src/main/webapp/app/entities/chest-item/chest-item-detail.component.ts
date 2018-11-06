import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChestItem } from 'app/shared/model/chest-item.model';

@Component({
    selector: 'jhi-chest-item-detail',
    templateUrl: './chest-item-detail.component.html'
})
export class ChestItemDetailComponent implements OnInit {
    chestItem: IChestItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chestItem }) => {
            this.chestItem = chestItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
