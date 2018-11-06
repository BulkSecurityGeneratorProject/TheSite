import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IChest } from 'app/shared/model/chest.model';
import { ChestService } from './chest.service';

@Component({
    selector: 'jhi-chest-update',
    templateUrl: './chest-update.component.html'
})
export class ChestUpdateComponent implements OnInit {
    chest: IChest;
    isSaving: boolean;

    constructor(private chestService: ChestService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chest }) => {
            this.chest = chest;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.chest.id !== undefined) {
            this.subscribeToSaveResponse(this.chestService.update(this.chest));
        } else {
            this.subscribeToSaveResponse(this.chestService.create(this.chest));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChest>>) {
        result.subscribe((res: HttpResponse<IChest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
