import { Moment } from 'moment';
import { ICliente } from 'app/shared/model/cliente.model';
import { IEquipo } from 'app/shared/model/equipo.model';
import { IProyecto } from 'app/shared/model/proyecto.model';

export interface IEquiposinstalados {
  id?: number;
  descripcion?: string;
  observacion?: string;
  fechainstalacion?: Moment;
  posibleactuliazcion?: boolean;
  cantidad?: number;
  cliente?: ICliente;
  equipo?: IEquipo;
  proyecto?: IProyecto;
}

export class Equiposinstalados implements IEquiposinstalados {
  constructor(
    public id?: number,
    public descripcion?: string,
    public observacion?: string,
    public fechainstalacion?: Moment,
    public posibleactuliazcion?: boolean,
    public cantidad?: number,
    public cliente?: ICliente,
    public equipo?: IEquipo,
    public proyecto?: IProyecto
  ) {
    this.posibleactuliazcion = this.posibleactuliazcion || false;
  }
}
