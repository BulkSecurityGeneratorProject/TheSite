import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChest } from 'app/shared/model/chest.model';

type EntityResponseType = HttpResponse<IChest>;
type EntityArrayResponseType = HttpResponse<IChest[]>;

@Injectable({ providedIn: 'root' })
export class ChestService {
    public resourceUrl = SERVER_API_URL + 'api/chests';

    constructor(private http: HttpClient) {}

    create(chest: IChest): Observable<EntityResponseType> {
        return this.http.post<IChest>(this.resourceUrl, chest, { observe: 'response' });
    }

    update(chest: IChest): Observable<EntityResponseType> {
        return this.http.put<IChest>(this.resourceUrl, chest, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IChest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IChest[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
