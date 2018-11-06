import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WienerMoneyTransferModule } from './money-transfer/money-transfer.module';
import { WienerChestModule } from './chest/chest.module';
import { WienerChestItemModule } from './chest-item/chest-item.module';
import { WienerTransactionModule } from './transaction/transaction.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        WienerMoneyTransferModule,
        WienerChestModule,
        WienerChestItemModule,
        WienerTransactionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WienerEntityModule {}
