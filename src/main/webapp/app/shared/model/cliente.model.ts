import { Moment } from 'moment';
import { IInteresesToCliente } from 'app/shared/model/intereses-to-cliente.model';
import { INovedadToCliente } from 'app/shared/model/novedad-to-cliente.model';
import { IEquiposinstalados } from 'app/shared/model/equiposinstalados.model';
import { ICotizacion } from 'app/shared/model/cotizacion.model';
import { IDealer } from 'app/shared/model/dealer.model';

export interface ICliente {
  id?: number;
  nombre?: string;
  apellido?: string;
  correo?: string;
  codigoDealer?: string;
  idciudad?: number;
  telefonocelular?: string;
  telefonofijo?: string;
  telefonoempresarial?: string;
  direccionresidencial?: string;
  direccionempresarial?: string;
  fechanacimiento?: Moment;
  idusuario?: string;
  imagenContentType?: string;
  imagen?: any;
  interesesToClientes?: IInteresesToCliente[];
  novedadToClientes?: INovedadToCliente[];
  equiposinstalados?: IEquiposinstalados[];
  cotizacions?: ICotizacion[];
  dealer?: IDealer;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellido?: string,
    public correo?: string,
    public codigoDealer?: string,
    public idciudad?: number,
    public telefonocelular?: string,
    public telefonofijo?: string,
    public telefonoempresarial?: string,
    public direccionresidencial?: string,
    public direccionempresarial?: string,
    public fechanacimiento?: Moment,
    public idusuario?: string,
    public imagenContentType?: string,
    public imagen?: any,
    public interesesToClientes?: IInteresesToCliente[],
    public novedadToClientes?: INovedadToCliente[],
    public equiposinstalados?: IEquiposinstalados[],
    public cotizacions?: ICotizacion[],
    public dealer?: IDealer
  ) {}
}
