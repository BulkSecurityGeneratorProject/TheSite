import { IChestItem } from 'app/shared/model//chest-item.model';

export interface ITransaction {
    id?: number;
    address?: string;
    notes?: string;
    name?: string;
    chestItems?: IChestItem[];
}

export class Transaction implements ITransaction {
    constructor(
        public id?: number,
        public address?: string,
        public notes?: string,
        public name?: string,
        public chestItems?: IChestItem[]
    ) {}
}
