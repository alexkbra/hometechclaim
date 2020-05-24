import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInteresesToCliente } from 'app/shared/model/intereses-to-cliente.model';

type EntityResponseType = HttpResponse<IInteresesToCliente>;
type EntityArrayResponseType = HttpResponse<IInteresesToCliente[]>;

@Injectable({ providedIn: 'root' })
export class InteresesToClienteService {
  public resourceUrl = SERVER_API_URL + 'api/intereses-to-clientes';

  constructor(protected http: HttpClient) {}

  create(interesesToCliente: IInteresesToCliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(interesesToCliente);
    return this.http
      .post<IInteresesToCliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(interesesToCliente: IInteresesToCliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(interesesToCliente);
    return this.http
      .put<IInteresesToCliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInteresesToCliente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInteresesToCliente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(interesesToCliente: IInteresesToCliente): IInteresesToCliente {
    const copy: IInteresesToCliente = Object.assign({}, interesesToCliente, {
      fechacreacion:
        interesesToCliente.fechacreacion && interesesToCliente.fechacreacion.isValid()
          ? interesesToCliente.fechacreacion.toJSON()
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
      res.body.forEach((interesesToCliente: IInteresesToCliente) => {
        interesesToCliente.fechacreacion = interesesToCliente.fechacreacion ? moment(interesesToCliente.fechacreacion) : undefined;
      });
    }
    return res;
  }
}
