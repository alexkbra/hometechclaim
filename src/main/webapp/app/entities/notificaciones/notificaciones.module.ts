import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { NotificacionesComponent } from './notificaciones.component';
import { NotificacionesDetailComponent } from './notificaciones-detail.component';
import { NotificacionesUpdateComponent } from './notificaciones-update.component';
import { NotificacionesDeleteDialogComponent } from './notificaciones-delete-dialog.component';
import { notificacionesRoute } from './notificaciones.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(notificacionesRoute)],
  declarations: [
    NotificacionesComponent,
    NotificacionesDetailComponent,
    NotificacionesUpdateComponent,
    NotificacionesDeleteDialogComponent
  ],
  entryComponents: [NotificacionesDeleteDialogComponent]
})
export class HometechclaimNotificacionesModule {}
