import { Moment } from 'moment';
import { IEquiposinstalados } from 'app/shared/model/equiposinstalados.model';

export interface IProyecto {
  id?: number;
  fechacreacion?: Moment;
  descripcion?: string;
  valoriva?: number;
  totalsiniva?: number;
  fechaultimomantenimiento?: Moment;
  porcentajedescuento?: number;
  total?: number;
  activo?: boolean;
  imagenContentType?: string;
  imagen?: any;
  equiposinstalados?: IEquiposinstalados[];
}

export class Proyecto implements IProyecto {
  constructor(
    public id?: number,
    public fechacreacion?: Moment,
    public descripcion?: string,
    public valoriva?: number,
    public totalsiniva?: number,
    public fechaultimomantenimiento?: Moment,
    public porcentajedescuento?: number,
    public total?: number,
    public activo?: boolean,
    public imagenContentType?: string,
    public imagen?: any,
    public equiposinstalados?: IEquiposinstalados[]
  ) {
    this.activo = this.activo || false;
  }
}
