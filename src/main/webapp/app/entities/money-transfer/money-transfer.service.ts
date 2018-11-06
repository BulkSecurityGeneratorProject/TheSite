import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMoneyTransfer } from 'app/shared/model/money-transfer.model';

type EntityResponseType = HttpResponse<IMoneyTransfer>;
type EntityArrayResponseType = HttpResponse<IMoneyTransfer[]>;

@Injectable({ providedIn: 'root' })
export class MoneyTransferService {
    public resourceUrl = SERVER_API_URL + 'api/money-transfers';

    constructor(private http: HttpClient) {}

    create(moneyTransfer: IMoneyTransfer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(moneyTransfer);
        return this.http
            .post<IMoneyTransfer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(moneyTransfer: IMoneyTransfer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(moneyTransfer);
        return this.http
            .put<IMoneyTransfer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMoneyTransfer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMoneyTransfer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(moneyTransfer: IMoneyTransfer): IMoneyTransfer {
        const copy: IMoneyTransfer = Object.assign({}, moneyTransfer, {
            payedTime:
                moneyTransfer.payedTime != null && moneyTransfer.payedTime.isValid() ? moneyTransfer.payedTime.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.payedTime = res.body.payedTime != null ? moment(res.body.payedTime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((moneyTransfer: IMoneyTransfer) => {
                moneyTransfer.payedTime = moneyTransfer.payedTime != null ? moment(moneyTransfer.payedTime) : null;
            });
        }
        return res;
    }
}
