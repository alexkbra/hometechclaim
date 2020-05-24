import { IRequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';
import { ITipoEquipo } from 'app/shared/model/tipo-equipo.model';
import { ITipoSolucion } from 'app/shared/model/tipo-solucion.model';

export interface ISolucion {
  id?: number;
  nombre?: string;
  descripcion?: any;
  code?: string;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  requerimientoToSolucions?: IRequerimientoToSolucion[];
  tipoEquipos?: ITipoEquipo[];
  tipoSolucion?: ITipoSolucion;
}

export class Solucion implements ISolucion {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: any,
    public code?: string,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public requerimientoToSolucions?: IRequerimientoToSolucion[],
    public tipoEquipos?: ITipoEquipo[],
    public tipoSolucion?: ITipoSolucion
  ) {}
}
