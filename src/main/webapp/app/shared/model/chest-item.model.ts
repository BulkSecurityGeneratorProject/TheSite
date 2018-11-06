import { IChest } from 'app/shared/model//chest.model';
import { ITransaction } from 'app/shared/model//transaction.model';

export interface IChestItem {
    id?: number;
    name?: string;
    type?: string;
    price?: number;
    numOfTimesRolled?: number;
    numOfTimesAccepted?: number;
    chest?: IChest;
    transactions?: ITransaction[];
}

export class ChestItem implements IChestItem {
    constructor(
        public id?: number,
        public name?: string,
        public type?: string,
        public price?: number,
        public numOfTimesRolled?: number,
        public numOfTimesAccepted?: number,
        public chest?: IChest,
        public transactions?: ITransaction[]
    ) {}
}
