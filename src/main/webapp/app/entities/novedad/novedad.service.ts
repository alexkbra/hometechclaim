import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INovedad } from 'app/shared/model/novedad.model';

type EntityResponseType = HttpResponse<INovedad>;
type EntityArrayResponseType = HttpResponse<INovedad[]>;

@Injectable({ providedIn: 'root' })
export class NovedadService {
  public resourceUrl = SERVER_API_URL + 'api/novedads';

  constructor(protected http: HttpClient) {}

  create(novedad: INovedad): Observable<EntityResponseType> {
    return this.http.post<INovedad>(this.resourceUrl, novedad, { observe: 'response' });
  }

  update(novedad: INovedad): Observable<EntityResponseType> {
    return this.http.put<INovedad>(this.resourceUrl, novedad, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INovedad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INovedad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
