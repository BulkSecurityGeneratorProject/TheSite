import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Chest } from 'app/shared/model/chest.model';
import { ChestService } from './chest.service';
import { ChestComponent } from './chest.component';
import { ChestDetailComponent } from './chest-detail.component';
import { ChestUpdateComponent } from './chest-update.component';
import { ChestDeletePopupComponent } from './chest-delete-dialog.component';
import { IChest } from 'app/shared/model/chest.model';

@Injectable({ providedIn: 'root' })
export class ChestResolve implements Resolve<IChest> {
    constructor(private service: ChestService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Chest> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Chest>) => response.ok),
                map((chest: HttpResponse<Chest>) => chest.body)
            );
        }
        return of(new Chest());
    }
}

export const chestRoute: Routes = [
    {
        path: 'chest',
        component: ChestComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.chest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chest/:id/view',
        component: ChestDetailComponent,
        resolve: {
            chest: ChestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.chest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chest/new',
        component: ChestUpdateComponent,
        resolve: {
            chest: ChestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.chest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chest/:id/edit',
        component: ChestUpdateComponent,
        resolve: {
            chest: ChestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.chest.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chestPopupRoute: Routes = [
    {
        path: 'chest/:id/delete',
        component: ChestDeletePopupComponent,
        resolve: {
            chest: ChestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wienerApp.chest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
