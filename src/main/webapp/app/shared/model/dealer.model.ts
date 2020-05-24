import { IColaboradores } from 'app/shared/model/colaboradores.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { IComerciales } from 'app/shared/model/comerciales.model';

export interface IDealer {
  id?: number;
  nombre?: string;
  correo?: string;
  codigo?: string;
  idciudad?: number;
  direccion?: string;
  telefonofijo?: string;
  telefonocelular?: string;
  idusuario?: string;
  colaboradores?: IColaboradores[];
  clientes?: ICliente[];
  comerciales?: IComerciales;
}

export class Dealer implements IDealer {
  constructor(
    public id?: number,
    public nombre?: string,
    public correo?: string,
    public codigo?: string,
    public idciudad?: number,
    public direccion?: string,
    public telefonofijo?: string,
    public telefonocelular?: string,
    public idusuario?: string,
    public colaboradores?: IColaboradores[],
    public clientes?: ICliente[],
    public comerciales?: IComerciales
  ) {}
}
