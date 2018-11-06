import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MoneyTransfer } from 'app/shared/model/money-transfer.model';
import { MoneyTransferService } from './money-transfer.service';
import { MoneyTransferComponent } from './money-transfer.component';
import { MoneyTransferDetailComponent } from './money-transfer-detail.component';
import { MoneyTransferUpdateComponent } from './money-transfer-update.component';
import { MoneyTransferDeletePopupComponent } from './money-transfer-delete-dialog.component';
import { IMoneyTransfer } from 'app/shared/model/money-transfer.model';

@Injectable({ providedIn: 'root' })
export class MoneyTransferResolve implements Resolve<IMoneyTransfer> {
    constructor(private service: MoneyTransferService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MoneyTransfer> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MoneyTransfer>) => response.ok),
                map((moneyTransfer: HttpResponse<MoneyTransfer>) => moneyTransfer.body)
            );
        }
        return of(new MoneyTransfer());
    }
}

export const moneyTransferRoute: Routes = [
    {
        path: 'money-transfer',
        component: MoneyTransferComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.moneyTransfer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'money-transfer/:id/view',
        component: MoneyTransferDetailComponent,
        resolve: {
            moneyTransfer: MoneyTransferResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.moneyTransfer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'money-transfer/new',
        component: MoneyTransferUpdateComponent,
        resolve: {
            moneyTransfer: MoneyTransferResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.moneyTransfer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'money-transfer/:id/edit',
        component: MoneyTransferUpdateComponent,
        resolve: {
            moneyTransfer: MoneyTransferResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.moneyTransfer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moneyTransferPopupRoute: Routes = [
    {
        path: 'money-transfer/:id/delete',
        component: MoneyTransferDeletePopupComponent,
        resolve: {
            moneyTransfer: MoneyTransferResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.moneyTransfer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
