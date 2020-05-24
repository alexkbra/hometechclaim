import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INotificaciones } from 'app/shared/model/notificaciones.model';

type EntityResponseType = HttpResponse<INotificaciones>;
type EntityArrayResponseType = HttpResponse<INotificaciones[]>;

@Injectable({ providedIn: 'root' })
export class NotificacionesService {
  public resourceUrl = SERVER_API_URL + 'api/notificaciones';

  constructor(protected http: HttpClient) {}

  create(notificaciones: INotificaciones): Observable<EntityResponseType> {
    return this.http.post<INotificaciones>(this.resourceUrl, notificaciones, { observe: 'response' });
  }

  update(notificaciones: INotificaciones): Observable<EntityResponseType> {
    return this.http.put<INotificaciones>(this.resourceUrl, notificaciones, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INotificaciones>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INotificaciones[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
