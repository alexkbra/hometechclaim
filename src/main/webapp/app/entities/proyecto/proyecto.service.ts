import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProyecto } from 'app/shared/model/proyecto.model';

type EntityResponseType = HttpResponse<IProyecto>;
type EntityArrayResponseType = HttpResponse<IProyecto[]>;

@Injectable({ providedIn: 'root' })
export class ProyectoService {
  public resourceUrl = SERVER_API_URL + 'api/proyectos';

  constructor(protected http: HttpClient) {}

  create(proyecto: IProyecto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proyecto);
    return this.http
      .post<IProyecto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(proyecto: IProyecto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proyecto);
    return this.http
      .put<IProyecto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProyecto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProyecto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(proyecto: IProyecto): IProyecto {
    const copy: IProyecto = Object.assign({}, proyecto, {
      fechacreacion: proyecto.fechacreacion && proyecto.fechacreacion.isValid() ? proyecto.fechacreacion.toJSON() : undefined,
      fechaultimomantenimiento:
        proyecto.fechaultimomantenimiento && proyecto.fechaultimomantenimiento.isValid()
          ? proyecto.fechaultimomantenimiento.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechacreacion = res.body.fechacreacion ? moment(res.body.fechacreacion) : undefined;
      res.body.fechaultimomantenimiento = res.body.fechaultimomantenimiento ? moment(res.body.fechaultimomantenimiento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((proyecto: IProyecto) => {
        proyecto.fechacreacion = proyecto.fechacreacion ? moment(proyecto.fechacreacion) : undefined;
        proyecto.fechaultimomantenimiento = proyecto.fechaultimomantenimiento ? moment(proyecto.fechaultimomantenimiento) : undefined;
      });
    }
    return res;
  }
}
