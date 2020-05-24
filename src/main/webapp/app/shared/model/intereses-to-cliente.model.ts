import { Moment } from 'moment';
import { ICliente } from 'app/shared/model/cliente.model';
import { IIntereses } from 'app/shared/model/intereses.model';

export interface IInteresesToCliente {
  id?: number;
  fechacreacion?: Moment;
  cliente?: ICliente;
  intereses?: IIntereses;
}

export class InteresesToCliente implements IInteresesToCliente {
  constructor(public id?: number, public fechacreacion?: Moment, public cliente?: ICliente, public intereses?: IIntereses) {}
}
