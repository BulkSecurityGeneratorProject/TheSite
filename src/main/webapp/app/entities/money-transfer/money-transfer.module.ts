import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WienerSharedModule } from 'app/shared';
import {
    MoneyTransferComponent,
    MoneyTransferDetailComponent,
    MoneyTransferUpdateComponent,
    MoneyTransferDeletePopupComponent,
    MoneyTransferDeleteDialogComponent,
    moneyTransferRoute,
    moneyTransferPopupRoute
} from './';

const ENTITY_STATES = [...moneyTransferRoute, ...moneyTransferPopupRoute];

@NgModule({
    imports: [WienerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MoneyTransferComponent,
        MoneyTransferDetailComponent,
        MoneyTransferUpdateComponent,
        MoneyTransferDeleteDialogComponent,
        MoneyTransferDeletePopupComponent
    ],
    entryComponents: [
        MoneyTransferComponent,
        MoneyTransferUpdateComponent,
        MoneyTransferDeleteDialogComponent,
        MoneyTransferDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WienerMoneyTransferModule {}
