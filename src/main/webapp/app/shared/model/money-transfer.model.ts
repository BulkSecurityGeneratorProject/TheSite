import { Moment } from 'moment';

export interface IMoneyTransfer {
    id?: number;
    payedAmount?: number;
    payedTime?: Moment;
    payedInCurrency?: string;
    paymentSuccessful?: boolean;
    paymentMode?: string;
}

export class MoneyTransfer implements IMoneyTransfer {
    constructor(
        public id?: number,
        public payedAmount?: number,
        public payedTime?: Moment,
        public payedInCurrency?: string,
        public paymentSuccessful?: boolean,
        public paymentMode?: string
    ) {
        this.paymentSuccessful = this.paymentSuccessful || false;
    }
}
