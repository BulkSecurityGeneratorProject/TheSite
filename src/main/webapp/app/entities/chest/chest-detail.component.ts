import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChest } from 'app/shared/model/chest.model';

@Component({
    selector: 'jhi-chest-detail',
    templateUrl: './chest-detail.component.html'
})
export class ChestDetailComponent implements OnInit {
    chest: IChest;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chest }) => {
            this.chest = chest;
        });
    }

    previousState() {
        window.history.back();
    }
}
