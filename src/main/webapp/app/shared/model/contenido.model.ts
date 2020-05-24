import { Moment } from 'moment';
import { INovedad } from 'app/shared/model/novedad.model';
import { TipoContenido } from 'app/shared/model/enumerations/tipo-contenido.model';

export interface IContenido {
  id?: number;
  descripcion?: any;
  videoUrl?: string;
  imagenURlContentType?: string;
  imagenURl?: any;
  audio?: string;
  tipoContenido?: TipoContenido;
  activo?: boolean;
  fechacreacion?: Moment;
  novedad?: INovedad;
}

export class Contenido implements IContenido {
  constructor(
    public id?: number,
    public descripcion?: any,
    public videoUrl?: string,
    public imagenURlContentType?: string,
    public imagenURl?: any,
    public audio?: string,
    public tipoContenido?: TipoContenido,
    public activo?: boolean,
    public fechacreacion?: Moment,
    public novedad?: INovedad
  ) {
    this.activo = this.activo || false;
  }
}
