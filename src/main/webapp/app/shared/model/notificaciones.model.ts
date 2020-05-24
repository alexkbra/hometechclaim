import { Areas } from 'app/shared/model/enumerations/areas.model';

export interface INotificaciones {
  id?: number;
  correo?: string;
  area?: Areas;
  activo?: boolean;
  idusuario?: string;
}

export class Notificaciones implements INotificaciones {
  constructor(public id?: number, public correo?: string, public area?: Areas, public activo?: boolean, public idusuario?: string) {
    this.activo = this.activo || false;
  }
}
