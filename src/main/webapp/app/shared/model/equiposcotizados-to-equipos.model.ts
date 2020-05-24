import { Moment } from 'moment';
import { ICotizacion } from 'app/shared/model/cotizacion.model';
import { IEquipo } from 'app/shared/model/equipo.model';

export interface IEquiposcotizadosToEquipos {
  id?: number;
  valorUnidad?: number;
  valorUtilUnidad?: number;
  descuentoUnidad?: number;
  fechacotizacion?: Moment;
  cantidadEquipos?: number;
  cotizacion?: ICotizacion;
  equiposcotizadosToEquipos?: IEquipo;
}

export class EquiposcotizadosToEquipos implements IEquiposcotizadosToEquipos {
  constructor(
    public id?: number,
    public valorUnidad?: number,
    public valorUtilUnidad?: number,
    public descuentoUnidad?: number,
    public fechacotizacion?: Moment,
    public cantidadEquipos?: number,
    public cotizacion?: ICotizacion,
    public equiposcotizadosToEquipos?: IEquipo
  ) {}
}
