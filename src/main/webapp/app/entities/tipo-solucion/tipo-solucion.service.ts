import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoSolucion } from 'app/shared/model/tipo-solucion.model';

type EntityResponseType = HttpResponse<ITipoSolucion>;
type EntityArrayResponseType = HttpResponse<ITipoSolucion[]>;

@Injectable({ providedIn: 'root' })
export class TipoSolucionService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-solucions';

  constructor(protected http: HttpClient) {}

  create(tipoSolucion: ITipoSolucion): Observable<EntityResponseType> {
    return this.http.post<ITipoSolucion>(this.resourceUrl, tipoSolucion, { observe: 'response' });
  }

  update(tipoSolucion: ITipoSolucion): Observable<EntityResponseType> {
    return this.http.put<ITipoSolucion>(this.resourceUrl, tipoSolucion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoSolucion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoSolucion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
