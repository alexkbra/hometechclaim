import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContenido } from 'app/shared/model/contenido.model';

type EntityResponseType = HttpResponse<IContenido>;
type EntityArrayResponseType = HttpResponse<IContenido[]>;

@Injectable({ providedIn: 'root' })
export class ContenidoService {
  public resourceUrl = SERVER_API_URL + 'api/contenidos';

  constructor(protected http: HttpClient) {}

  create(contenido: IContenido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contenido);
    return this.http
      .post<IContenido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contenido: IContenido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contenido);
    return this.http
      .put<IContenido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContenido>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContenido[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contenido: IContenido): IContenido {
    const copy: IContenido = Object.assign({}, contenido, {
      fechacreacion: contenido.fechacreacion && contenido.fechacreacion.isValid() ? contenido.fechacreacion.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechacreacion = res.body.fechacreacion ? moment(res.body.fechacreacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((contenido: IContenido) => {
        contenido.fechacreacion = contenido.fechacreacion ? moment(contenido.fechacreacion) : undefined;
      });
    }
    return res;
  }
}
