import { INovedadToCliente } from 'app/shared/model/novedad-to-cliente.model';
import { IContenido } from 'app/shared/model/contenido.model';

export interface INovedad {
  id?: number;
  titulo?: string;
  subtitulo?: string;
  descripcion?: string;
  imagenContentType?: string;
  imagen?: any;
  novedadToClientes?: INovedadToCliente[];
  contenidos?: IContenido[];
}

export class Novedad implements INovedad {
  constructor(
    public id?: number,
    public titulo?: string,
    public subtitulo?: string,
    public descripcion?: string,
    public imagenContentType?: string,
    public imagen?: any,
    public novedadToClientes?: INovedadToCliente[],
    public contenidos?: IContenido[]
  ) {}
}
