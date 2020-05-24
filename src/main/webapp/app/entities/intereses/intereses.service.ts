import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIntereses } from 'app/shared/model/intereses.model';

type EntityResponseType = HttpResponse<IIntereses>;
type EntityArrayResponseType = HttpResponse<IIntereses[]>;

@Injectable({ providedIn: 'root' })
export class InteresesService {
  public resourceUrl = SERVER_API_URL + 'api/intereses';

  constructor(protected http: HttpClient) {}

  create(intereses: IIntereses): Observable<EntityResponseType> {
    return this.http.post<IIntereses>(this.resourceUrl, intereses, { observe: 'response' });
  }

  update(intereses: IIntereses): Observable<EntityResponseType> {
    return this.http.put<IIntereses>(this.resourceUrl, intereses, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIntereses>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIntereses[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
