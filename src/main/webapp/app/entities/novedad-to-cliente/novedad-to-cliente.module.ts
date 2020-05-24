import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { NovedadToClienteComponent } from './novedad-to-cliente.component';
import { NovedadToClienteDetailComponent } from './novedad-to-cliente-detail.component';
import { NovedadToClienteUpdateComponent } from './novedad-to-cliente-update.component';
import { NovedadToClienteDeleteDialogComponent } from './novedad-to-cliente-delete-dialog.component';
import { novedadToClienteRoute } from './novedad-to-cliente.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(novedadToClienteRoute)],
  declarations: [
    NovedadToClienteComponent,
    NovedadToClienteDetailComponent,
    NovedadToClienteUpdateComponent,
    NovedadToClienteDeleteDialogComponent
  ],
  entryComponents: [NovedadToClienteDeleteDialogComponent]
})
export class HometechclaimNovedadToClienteModule {}
