import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IColaboradores } from 'app/shared/model/colaboradores.model';

type EntityResponseType = HttpResponse<IColaboradores>;
type EntityArrayResponseType = HttpResponse<IColaboradores[]>;

@Injectable({ providedIn: 'root' })
export class ColaboradoresService {
  public resourceUrl = SERVER_API_URL + 'api/colaboradores';

  constructor(protected http: HttpClient) {}

  create(colaboradores: IColaboradores): Observable<EntityResponseType> {
    return this.http.post<IColaboradores>(this.resourceUrl, colaboradores, { observe: 'response' });
  }

  update(colaboradores: IColaboradores): Observable<EntityResponseType> {
    return this.http.put<IColaboradores>(this.resourceUrl, colaboradores, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IColaboradores>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IColaboradores[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
