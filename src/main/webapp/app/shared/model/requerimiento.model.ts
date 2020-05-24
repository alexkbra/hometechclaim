import { Moment } from 'moment';
import { IRequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';
import { EstadoRequerimiento } from 'app/shared/model/enumerations/estado-requerimiento.model';
import { TipoRequerimiento } from 'app/shared/model/enumerations/tipo-requerimiento.model';

export interface IRequerimiento {
  id?: number;
  detalleproblema?: any;
  fechacreacion?: Moment;
  fechaactualizacion?: Moment;
  observaciones?: any;
  idusuarioatendio?: string;
  idusuarioactualizo?: string;
  idUsuario?: string;
  imagenContentType?: string;
  imagen?: any;
  estadoRequerimiento?: EstadoRequerimiento;
  tipoRequerimiento?: TipoRequerimiento;
  requerimientoToSolucions?: IRequerimientoToSolucion[];
}

export class Requerimiento implements IRequerimiento {
  constructor(
    public id?: number,
    public detalleproblema?: any,
    public fechacreacion?: Moment,
    public fechaactualizacion?: Moment,
    public observaciones?: any,
    public idusuarioatendio?: string,
    public idusuarioactualizo?: string,
    public idUsuario?: string,
    public imagenContentType?: string,
    public imagen?: any,
    public estadoRequerimiento?: EstadoRequerimiento,
    public tipoRequerimiento?: TipoRequerimiento,
    public requerimientoToSolucions?: IRequerimientoToSolucion[]
  ) {}
}
