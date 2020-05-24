import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';

type EntityResponseType = HttpResponse<IRequerimientoToSolucion>;
type EntityArrayResponseType = HttpResponse<IRequerimientoToSolucion[]>;

@Injectable({ providedIn: 'root' })
export class RequerimientoToSolucionService {
  public resourceUrl = SERVER_API_URL + 'api/requerimiento-to-solucions';

  constructor(protected http: HttpClient) {}

  create(requerimientoToSolucion: IRequerimientoToSolucion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requerimientoToSolucion);
    return this.http
      .post<IRequerimientoToSolucion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(requerimientoToSolucion: IRequerimientoToSolucion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requerimientoToSolucion);
    return this.http
      .put<IRequerimientoToSolucion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRequerimientoToSolucion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRequerimientoToSolucion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(requerimientoToSolucion: IRequerimientoToSolucion): IRequerimientoToSolucion {
    const copy: IRequerimientoToSolucion = Object.assign({}, requerimientoToSolucion, {
      fechacreacion:
        requerimientoToSolucion.fechacreacion && requerimientoToSolucion.fechacreacion.isValid()
          ? requerimientoToSolucion.fechacreacion.toJSON()
          : undefined
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
      res.body.forEach((requerimientoToSolucion: IRequerimientoToSolucion) => {
        requerimientoToSolucion.fechacreacion = requerimientoToSolucion.fechacreacion
          ? moment(requerimientoToSolucion.fechacreacion)
          : undefined;
      });
    }
    return res;
  }
}
