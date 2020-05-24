import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { TipoEquipoComponent } from './tipo-equipo.component';
import { TipoEquipoDetailComponent } from './tipo-equipo-detail.component';
import { TipoEquipoUpdateComponent } from './tipo-equipo-update.component';
import { TipoEquipoDeleteDialogComponent } from './tipo-equipo-delete-dialog.component';
import { tipoEquipoRoute } from './tipo-equipo.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(tipoEquipoRoute)],
  declarations: [TipoEquipoComponent, TipoEquipoDetailComponent, TipoEquipoUpdateComponent, TipoEquipoDeleteDialogComponent],
  entryComponents: [TipoEquipoDeleteDialogComponent]
})
export class HometechclaimTipoEquipoModule {}
