import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'dispositivo',
        loadChildren: () => import('./dispositivo/dispositivo.module').then(m => m.HometechclaimDispositivoModule)
      },
      {
        path: 'dealer',
        loadChildren: () => import('./dealer/dealer.module').then(m => m.HometechclaimDealerModule)
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.HometechclaimClienteModule)
      },
      {
        path: 'proyecto',
        loadChildren: () => import('./proyecto/proyecto.module').then(m => m.HometechclaimProyectoModule)
      },
      {
        path: 'equiposinstalados',
        loadChildren: () => import('./equiposinstalados/equiposinstalados.module').then(m => m.HometechclaimEquiposinstaladosModule)
      },
      {
        path: 'departamentos',
        loadChildren: () => import('./departamentos/departamentos.module').then(m => m.HometechclaimDepartamentosModule)
      },
      {
        path: 'municipios',
        loadChildren: () => import('./municipios/municipios.module').then(m => m.HometechclaimMunicipiosModule)
      },
      {
        path: 'ciudad',
        loadChildren: () => import('./ciudad/ciudad.module').then(m => m.HometechclaimCiudadModule)
      },
      {
        path: 'cotizacion',
        loadChildren: () => import('./cotizacion/cotizacion.module').then(m => m.HometechclaimCotizacionModule)
      },
      {
        path: 'equiposcotizados-to-equipos',
        loadChildren: () =>
          import('./equiposcotizados-to-equipos/equiposcotizados-to-equipos.module').then(
            m => m.HometechclaimEquiposcotizadosToEquiposModule
          )
      },
      {
        path: 'requerimiento',
        loadChildren: () => import('./requerimiento/requerimiento.module').then(m => m.HometechclaimRequerimientoModule)
      },
      {
        path: 'requerimiento-to-solucion',
        loadChildren: () =>
          import('./requerimiento-to-solucion/requerimiento-to-solucion.module').then(m => m.HometechclaimRequerimientoToSolucionModule)
      },
      {
        path: 'tipo-solucion',
        loadChildren: () => import('./tipo-solucion/tipo-solucion.module').then(m => m.HometechclaimTipoSolucionModule)
      },
      {
        path: 'solucion',
        loadChildren: () => import('./solucion/solucion.module').then(m => m.HometechclaimSolucionModule)
      },
      {
        path: 'tipo-equipo',
        loadChildren: () => import('./tipo-equipo/tipo-equipo.module').then(m => m.HometechclaimTipoEquipoModule)
      },
      {
        path: 'marca',
        loadChildren: () => import('./marca/marca.module').then(m => m.HometechclaimMarcaModule)
      },
      {
        path: 'equipo',
        loadChildren: () => import('./equipo/equipo.module').then(m => m.HometechclaimEquipoModule)
      },
      {
        path: 'novedad',
        loadChildren: () => import('./novedad/novedad.module').then(m => m.HometechclaimNovedadModule)
      },
      {
        path: 'contenido',
        loadChildren: () => import('./contenido/contenido.module').then(m => m.HometechclaimContenidoModule)
      },
      {
        path: 'novedad-to-cliente',
        loadChildren: () => import('./novedad-to-cliente/novedad-to-cliente.module').then(m => m.HometechclaimNovedadToClienteModule)
      },
      {
        path: 'intereses',
        loadChildren: () => import('./intereses/intereses.module').then(m => m.HometechclaimInteresesModule)
      },
      {
        path: 'intereses-to-cliente',
        loadChildren: () => import('./intereses-to-cliente/intereses-to-cliente.module').then(m => m.HometechclaimInteresesToClienteModule)
      },
      {
        path: 'comerciales',
        loadChildren: () => import('./comerciales/comerciales.module').then(m => m.HometechclaimComercialesModule)
      },
      {
        path: 'colaboradores',
        loadChildren: () => import('./colaboradores/colaboradores.module').then(m => m.HometechclaimColaboradoresModule)
      },
      {
        path: 'notificaciones',
        loadChildren: () => import('./notificaciones/notificaciones.module').then(m => m.HometechclaimNotificacionesModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class HometechclaimEntityModule {}
