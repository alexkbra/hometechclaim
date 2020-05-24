import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IComerciales } from 'app/shared/model/comerciales.model';

type EntityResponseType = HttpResponse<IComerciales>;
type EntityArrayResponseType = HttpResponse<IComerciales[]>;

@Injectable({ providedIn: 'root' })
export class ComercialesService {
  public resourceUrl = SERVER_API_URL + 'api/comerciales';

  constructor(protected http: HttpClient) {}

  create(comerciales: IComerciales): Observable<EntityResponseType> {
    return this.http.post<IComerciales>(this.resourceUrl, comerciales, { observe: 'response' });
  }

  update(comerciales: IComerciales): Observable<EntityResponseType> {
    return this.http.put<IComerciales>(this.resourceUrl, comerciales, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComerciales>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComerciales[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
