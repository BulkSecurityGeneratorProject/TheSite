import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoneyTransfer } from 'app/shared/model/money-transfer.model';

@Component({
    selector: 'jhi-money-transfer-detail',
    templateUrl: './money-transfer-detail.component.html'
})
export class MoneyTransferDetailComponent implements OnInit {
    moneyTransfer: IMoneyTransfer;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moneyTransfer }) => {
            this.moneyTransfer = moneyTransfer;
        });
    }

    previousState() {
        window.history.back();
    }
}
