import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ChestItem } from 'app/shared/model/chest-item.model';
import { ChestItemService } from './chest-item.service';
import { ChestItemComponent } from './chest-item.component';
import { ChestItemDetailComponent } from './chest-item-detail.component';
import { ChestItemUpdateComponent } from './chest-item-update.component';
import { ChestItemDeletePopupComponent } from './chest-item-delete-dialog.component';
import { IChestItem } from 'app/shared/model/chest-item.model';

@Injectable({ providedIn: 'root' })
export class ChestItemResolve implements Resolve<IChestItem> {
    constructor(private service: ChestItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ChestItem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ChestItem>) => response.ok),
                map((chestItem: HttpResponse<ChestItem>) => chestItem.body)
            );
        }
        return of(new ChestItem());
    }
}

export const chestItemRoute: Routes = [
    {
        path: 'chest-item',
        component: ChestItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.chestItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chest-item/:id/view',
        component: ChestItemDetailComponent,
        resolve: {
            chestItem: ChestItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.chestItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chest-item/new',
        component: ChestItemUpdateComponent,
        resolve: {
            chestItem: ChestItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.chestItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chest-item/:id/edit',
        component: ChestItemUpdateComponent,
        resolve: {
            chestItem: ChestItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.chestItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chestItemPopupRoute: Routes = [
    {
        path: 'chest-item/:id/delete',
        component: ChestItemDeletePopupComponent,
        resolve: {
            chestItem: ChestItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.chestItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
