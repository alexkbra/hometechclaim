import { IInteresesToCliente } from 'app/shared/model/intereses-to-cliente.model';

export interface IIntereses {
  id?: number;
  nombre?: string;
  descripcion?: string;
  imagen?: string;
  code?: string;
  interesesToClientes?: IInteresesToCliente[];
}

export class Intereses implements IIntereses {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public imagen?: string,
    public code?: string,
    public interesesToClientes?: IInteresesToCliente[]
  ) {}
}
