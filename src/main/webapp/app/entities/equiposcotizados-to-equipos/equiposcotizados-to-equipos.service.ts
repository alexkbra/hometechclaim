import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';

type EntityResponseType = HttpResponse<IEquiposcotizadosToEquipos>;
type EntityArrayResponseType = HttpResponse<IEquiposcotizadosToEquipos[]>;

@Injectable({ providedIn: 'root' })
export class EquiposcotizadosToEquiposService {
  public resourceUrl = SERVER_API_URL + 'api/equiposcotizados-to-equipos';

  constructor(protected http: HttpClient) {}

  create(equiposcotizadosToEquipos: IEquiposcotizadosToEquipos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(equiposcotizadosToEquipos);
    return this.http
      .post<IEquiposcotizadosToEquipos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(equiposcotizadosToEquipos: IEquiposcotizadosToEquipos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(equiposcotizadosToEquipos);
    return this.http
      .put<IEquiposcotizadosToEquipos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEquiposcotizadosToEquipos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEquiposcotizadosToEquipos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(equiposcotizadosToEquipos: IEquiposcotizadosToEquipos): IEquiposcotizadosToEquipos {
    const copy: IEquiposcotizadosToEquipos = Object.assign({}, equiposcotizadosToEquipos, {
      fechacotizacion:
        equiposcotizadosToEquipos.fechacotizacion && equiposcotizadosToEquipos.fechacotizacion.isValid()
          ? equiposcotizadosToEquipos.fechacotizacion.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechacotizacion = res.body.fechacotizacion ? moment(res.body.fechacotizacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((equiposcotizadosToEquipos: IEquiposcotizadosToEquipos) => {
        equiposcotizadosToEquipos.fechacotizacion = equiposcotizadosToEquipos.fechacotizacion
          ? moment(equiposcotizadosToEquipos.fechacotizacion)
          : undefined;
      });
    }
    return res;
  }
}
