import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WienerSharedModule } from 'app/shared';
import {
    ChestComponent,
    ChestDetailComponent,
    ChestUpdateComponent,
    ChestDeletePopupComponent,
    ChestDeleteDialogComponent,
    chestRoute,
    chestPopupRoute
} from './';

const ENTITY_STATES = [...chestRoute, ...chestPopupRoute];

@NgModule({
    imports: [WienerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ChestComponent, ChestDetailComponent, ChestUpdateComponent, ChestDeleteDialogComponent, ChestDeletePopupComponent],
    entryComponents: [ChestComponent, ChestUpdateComponent, ChestDeleteDialogComponent, ChestDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WienerChestModule {}
