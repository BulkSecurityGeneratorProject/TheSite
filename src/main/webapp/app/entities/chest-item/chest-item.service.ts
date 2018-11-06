import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChestItem } from 'app/shared/model/chest-item.model';

type EntityResponseType = HttpResponse<IChestItem>;
type EntityArrayResponseType = HttpResponse<IChestItem[]>;

@Injectable({ providedIn: 'root' })
export class ChestItemService {
    public resourceUrl = SERVER_API_URL + 'api/chest-items';

    constructor(private http: HttpClient) {}

    create(chestItem: IChestItem): Observable<EntityResponseType> {
        return this.http.post<IChestItem>(this.resourceUrl, chestItem, { observe: 'response' });
    }

    update(chestItem: IChestItem): Observable<EntityResponseType> {
        return this.http.put<IChestItem>(this.resourceUrl, chestItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IChestItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IChestItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
