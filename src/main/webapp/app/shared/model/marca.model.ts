import { IEquipo } from 'app/shared/model/equipo.model';
import { ITipoEquipo } from 'app/shared/model/tipo-equipo.model';

export interface IMarca {
  id?: number;
  nombre?: string;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  code?: string;
  equipos?: IEquipo[];
  tipoEquipo?: ITipoEquipo;
}

export class Marca implements IMarca {
  constructor(
    public id?: number,
    public nombre?: string,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public code?: string,
    public equipos?: IEquipo[],
    public tipoEquipo?: ITipoEquipo
  ) {}
}
