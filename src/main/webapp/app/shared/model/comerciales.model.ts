import { IDealer } from 'app/shared/model/dealer.model';

export interface IComerciales {
  id?: number;
  codigo?: string;
  nombre?: string;
  descripcion?: any;
  correo?: string;
  zona?: string;
  idciudad?: number;
  idusuario?: string;
  dealers?: IDealer[];
}

export class Comerciales implements IComerciales {
  constructor(
    public id?: number,
    public codigo?: string,
    public nombre?: string,
    public descripcion?: any,
    public correo?: string,
    public zona?: string,
    public idciudad?: number,
    public idusuario?: string,
    public dealers?: IDealer[]
  ) {}
}
