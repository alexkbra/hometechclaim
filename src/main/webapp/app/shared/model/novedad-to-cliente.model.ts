import { Moment } from 'moment';
import { INovedad } from 'app/shared/model/novedad.model';
import { ICliente } from 'app/shared/model/cliente.model';

export interface INovedadToCliente {
  id?: number;
  fechacreacion?: Moment;
  novedad?: INovedad;
  cliente?: ICliente;
}

export class NovedadToCliente implements INovedadToCliente {
  constructor(public id?: number, public fechacreacion?: Moment, public novedad?: INovedad, public cliente?: ICliente) {}
}
