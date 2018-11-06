import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IChestItem } from 'app/shared/model/chest-item.model';
import { ChestItemService } from './chest-item.service';
import { IChest } from 'app/shared/model/chest.model';
import { ChestService } from 'app/entities/chest';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction';

@Component({
    selector: 'jhi-chest-item-update',
    templateUrl: './chest-item-update.component.html'
})
export class ChestItemUpdateComponent implements OnInit {
    chestItem: IChestItem;
    isSaving: boolean;

    chests: IChest[];

    transactions: ITransaction[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private chestItemService: ChestItemService,
        private chestService: ChestService,
        private transactionService: TransactionService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chestItem }) => {
            this.chestItem = chestItem;
        });
        this.chestService.query().subscribe(
            (res: HttpResponse<IChest[]>) => {
                this.chests = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.transactionService.query().subscribe(
            (res: HttpResponse<ITransaction[]>) => {
                this.transactions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.chestItem.id !== undefined) {
            this.subscribeToSaveResponse(this.chestItemService.update(this.chestItem));
        } else {
            this.subscribeToSaveResponse(this.chestItemService.create(this.chestItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChestItem>>) {
        result.subscribe((res: HttpResponse<IChestItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackChestById(index: number, item: IChest) {
        return item.id;
    }

    trackTransactionById(index: number, item: ITransaction) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
