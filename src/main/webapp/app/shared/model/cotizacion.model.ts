import { Moment } from 'moment';
import { IEquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { Estadocotizacion } from 'app/shared/model/enumerations/estadocotizacion.model';

export interface ICotizacion {
  id?: number;
  fechacreacion?: Moment;
  descripcion?: string;
  valoriva?: number;
  totalsiniva?: number;
  porcentajedescuento?: number;
  fechamantenimiento?: Moment;
  activo?: boolean;
  total?: number;
  estadocotizacion?: Estadocotizacion;
  equiposcotizadosToEquipos?: IEquiposcotizadosToEquipos[];
  cliente?: ICliente;
}

export class Cotizacion implements ICotizacion {
  constructor(
    public id?: number,
    public fechacreacion?: Moment,
    public descripcion?: string,
    public valoriva?: number,
    public totalsiniva?: number,
    public porcentajedescuento?: number,
    public fechamantenimiento?: Moment,
    public activo?: boolean,
    public total?: number,
    public estadocotizacion?: Estadocotizacion,
    public equiposcotizadosToEquipos?: IEquiposcotizadosToEquipos[],
    public cliente?: ICliente
  ) {
    this.activo = this.activo || false;
  }
}
