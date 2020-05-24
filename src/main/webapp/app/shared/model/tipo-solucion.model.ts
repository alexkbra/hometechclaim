import { ISolucion } from 'app/shared/model/solucion.model';

export interface ITipoSolucion {
  id?: number;
  nombre?: string;
  code?: string;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  solucions?: ISolucion[];
}

export class TipoSolucion implements ITipoSolucion {
  constructor(
    public id?: number,
    public nombre?: string,
    public code?: string,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public solucions?: ISolucion[]
  ) {}
}
