import { IChestItem } from 'app/shared/model//chest-item.model';

export interface IChest {
    id?: number;
    name?: string;
    type?: string;
    rollPrice?: number;
    numOfTimesRolled?: number;
    chestItems?: IChestItem[];
}

export class Chest implements IChest {
    constructor(
        public id?: number,
        public name?: string,
        public type?: string,
        public rollPrice?: number,
        public numOfTimesRolled?: number,
        public chestItems?: IChestItem[]
    ) {}
}
