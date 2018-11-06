import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IMoneyTransfer } from 'app/shared/model/money-transfer.model';
import { MoneyTransferService } from './money-transfer.service';

@Component({
    selector: 'jhi-money-transfer-update',
    templateUrl: './money-transfer-update.component.html'
})
export class MoneyTransferUpdateComponent implements OnInit {
    moneyTransfer: IMoneyTransfer;
    isSaving: boolean;
    payedTimeDp: any;

    constructor(private moneyTransferService: MoneyTransferService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ moneyTransfer }) => {
            this.moneyTransfer = moneyTransfer;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.moneyTransfer.id !== undefined) {
            this.subscribeToSaveResponse(this.moneyTransferService.update(this.moneyTransfer));
        } else {
            this.subscribeToSaveResponse(this.moneyTransferService.create(this.moneyTransfer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMoneyTransfer>>) {
        result.subscribe((res: HttpResponse<IMoneyTransfer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
