import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICotizacion } from 'app/shared/model/cotizacion.model';

type EntityResponseType = HttpResponse<ICotizacion>;
type EntityArrayResponseType = HttpResponse<ICotizacion[]>;

@Injectable({ providedIn: 'root' })
export class CotizacionService {
  public resourceUrl = SERVER_API_URL + 'api/cotizacions';

  constructor(protected http: HttpClient) {}

  create(cotizacion: ICotizacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cotizacion);
    return this.http
      .post<ICotizacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cotizacion: ICotizacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cotizacion);
    return this.http
      .put<ICotizacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICotizacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICotizacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cotizacion: ICotizacion): ICotizacion {
    const copy: ICotizacion = Object.assign({}, cotizacion, {
      fechacreacion: cotizacion.fechacreacion && cotizacion.fechacreacion.isValid() ? cotizacion.fechacreacion.toJSON() : undefined,
      fechamantenimiento:
        cotizacion.fechamantenimiento && cotizacion.fechamantenimiento.isValid() ? cotizacion.fechamantenimiento.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechacreacion = res.body.fechacreacion ? moment(res.body.fechacreacion) : undefined;
      res.body.fechamantenimiento = res.body.fechamantenimiento ? moment(res.body.fechamantenimiento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cotizacion: ICotizacion) => {
        cotizacion.fechacreacion = cotizacion.fechacreacion ? moment(cotizacion.fechacreacion) : undefined;
        cotizacion.fechamantenimiento = cotizacion.fechamantenimiento ? moment(cotizacion.fechamantenimiento) : undefined;
      });
    }
    return res;
  }
}
