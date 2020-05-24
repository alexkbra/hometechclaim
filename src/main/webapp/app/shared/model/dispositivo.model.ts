export interface IDispositivo {
  id?: number;
  idusuario?: string;
  idcliente?: string;
  iddealer?: string;
  activo?: boolean;
  dispositivo?: string;
}

export class Dispositivo implements IDispositivo {
  constructor(
    public id?: number,
    public idusuario?: string,
    public idcliente?: string,
    public iddealer?: string,
    public activo?: boolean,
    public dispositivo?: string
  ) {
    this.activo = this.activo || false;
  }
}
