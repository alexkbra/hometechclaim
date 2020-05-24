import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INovedadToCliente } from 'app/shared/model/novedad-to-cliente.model';

type EntityResponseType = HttpResponse<INovedadToCliente>;
type EntityArrayResponseType = HttpResponse<INovedadToCliente[]>;

@Injectable({ providedIn: 'root' })
export class NovedadToClienteService {
  public resourceUrl = SERVER_API_URL + 'api/novedad-to-clientes';

  constructor(protected http: HttpClient) {}

  create(novedadToCliente: INovedadToCliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(novedadToCliente);
    return this.http
      .post<INovedadToCliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(novedadToCliente: INovedadToCliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(novedadToCliente);
    return this.http
      .put<INovedadToCliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INovedadToCliente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INovedadToCliente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(novedadToCliente: INovedadToCliente): INovedadToCliente {
    const copy: INovedadToCliente = Object.assign({}, novedadToCliente, {
      fechacreacion:
        novedadToCliente.fechacreacion && novedadToCliente.fechacreacion.isValid() ? novedadToCliente.fechacreacion.toJSON() : undefined
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
      res.body.forEach((novedadToCliente: INovedadToCliente) => {
        novedadToCliente.fechacreacion = novedadToCliente.fechacreacion ? moment(novedadToCliente.fechacreacion) : undefined;
      });
    }
    return res;
  }
}
