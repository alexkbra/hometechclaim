import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';

type EntityResponseType = HttpResponse<IRequerimiento>;
type EntityArrayResponseType = HttpResponse<IRequerimiento[]>;

@Injectable({ providedIn: 'root' })
export class RequerimientoService {
  public resourceUrl = SERVER_API_URL + 'api/requerimientos';

  constructor(protected http: HttpClient) {}

  create(requerimiento: IRequerimiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requerimiento);
    return this.http
      .post<IRequerimiento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(requerimiento: IRequerimiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requerimiento);
    return this.http
      .put<IRequerimiento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRequerimiento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRequerimiento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(requerimiento: IRequerimiento): IRequerimiento {
    const copy: IRequerimiento = Object.assign({}, requerimiento, {
      fechacreacion:
        requerimiento.fechacreacion && requerimiento.fechacreacion.isValid() ? requerimiento.fechacreacion.toJSON() : undefined,
      fechaactualizacion:
        requerimiento.fechaactualizacion && requerimiento.fechaactualizacion.isValid()
          ? requerimiento.fechaactualizacion.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechacreacion = res.body.fechacreacion ? moment(res.body.fechacreacion) : undefined;
      res.body.fechaactualizacion = res.body.fechaactualizacion ? moment(res.body.fechaactualizacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((requerimiento: IRequerimiento) => {
        requerimiento.fechacreacion = requerimiento.fechacreacion ? moment(requerimiento.fechacreacion) : undefined;
        requerimiento.fechaactualizacion = requerimiento.fechaactualizacion ? moment(requerimiento.fechaactualizacion) : undefined;
      });
    }
    return res;
  }
}
