import { IMarca } from 'app/shared/model/marca.model';
import { ISolucion } from 'app/shared/model/solucion.model';

export interface ITipoEquipo {
  id?: number;
  nombre?: string;
  descripcion?: any;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  code?: string;
  marcas?: IMarca[];
  solucion?: ISolucion;
}

export class TipoEquipo implements ITipoEquipo {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: any,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public code?: string,
    public marcas?: IMarca[],
    public solucion?: ISolucion
  ) {}
}
