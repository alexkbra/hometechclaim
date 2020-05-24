import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISolucion } from 'app/shared/model/solucion.model';

type EntityResponseType = HttpResponse<ISolucion>;
type EntityArrayResponseType = HttpResponse<ISolucion[]>;

@Injectable({ providedIn: 'root' })
export class SolucionService {
  public resourceUrl = SERVER_API_URL + 'api/solucions';

  constructor(protected http: HttpClient) {}

  create(solucion: ISolucion): Observable<EntityResponseType> {
    return this.http.post<ISolucion>(this.resourceUrl, solucion, { observe: 'response' });
  }

  update(solucion: ISolucion): Observable<EntityResponseType> {
    return this.http.put<ISolucion>(this.resourceUrl, solucion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISolucion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISolucion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
