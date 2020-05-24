import { IDealer } from 'app/shared/model/dealer.model';

export interface IColaboradores {
  id?: number;
  nombre?: string;
  correo?: string;
  activo?: boolean;
  idusuario?: string;
  dealer?: IDealer;
}

export class Colaboradores implements IColaboradores {
  constructor(
    public id?: number,
    public nombre?: string,
    public correo?: string,
    public activo?: boolean,
    public idusuario?: string,
    public dealer?: IDealer
  ) {
    this.activo = this.activo || false;
  }
}
