import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { TipoSolucionComponent } from './tipo-solucion.component';
import { TipoSolucionDetailComponent } from './tipo-solucion-detail.component';
import { TipoSolucionUpdateComponent } from './tipo-solucion-update.component';
import { TipoSolucionDeleteDialogComponent } from './tipo-solucion-delete-dialog.component';
import { tipoSolucionRoute } from './tipo-solucion.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(tipoSolucionRoute)],
  declarations: [TipoSolucionComponent, TipoSolucionDetailComponent, TipoSolucionUpdateComponent, TipoSolucionDeleteDialogComponent],
  entryComponents: [TipoSolucionDeleteDialogComponent]
})
export class HometechclaimTipoSolucionModule {}
