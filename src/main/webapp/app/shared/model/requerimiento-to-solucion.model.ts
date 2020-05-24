import { Moment } from 'moment';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { ISolucion } from 'app/shared/model/solucion.model';

export interface IRequerimientoToSolucion {
  id?: number;
  fechacreacion?: Moment;
  requerimiento?: IRequerimiento;
  solucion?: ISolucion;
}

export class RequerimientoToSolucion implements IRequerimientoToSolucion {
  constructor(public id?: number, public fechacreacion?: Moment, public requerimiento?: IRequerimiento, public solucion?: ISolucion) {}
}
