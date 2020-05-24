import { IEquiposinstalados } from 'app/shared/model/equiposinstalados.model';
import { IEquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';
import { IMarca } from 'app/shared/model/marca.model';

export interface IEquipo {
  id?: number;
  nombre?: string;
  version?: string;
  controlador?: string;
  accountname?: string;
  controllermacaddress?: string;
  imagenUrlContentType?: string;
  imagenUrl?: any;
  valor?: number;
  equiposinstalados?: IEquiposinstalados[];
  equiposcotizadosToEquipos?: IEquiposcotizadosToEquipos[];
  marca?: IMarca;
}

export class Equipo implements IEquipo {
  constructor(
    public id?: number,
    public nombre?: string,
    public version?: string,
    public controlador?: string,
    public accountname?: string,
    public controllermacaddress?: string,
    public imagenUrlContentType?: string,
    public imagenUrl?: any,
    public valor?: number,
    public equiposinstalados?: IEquiposinstalados[],
    public equiposcotizadosToEquipos?: IEquiposcotizadosToEquipos[],
    public marca?: IMarca
  ) {}
}
