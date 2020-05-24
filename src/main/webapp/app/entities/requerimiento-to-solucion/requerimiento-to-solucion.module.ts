import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { RequerimientoToSolucionComponent } from './requerimiento-to-solucion.component';
import { RequerimientoToSolucionDetailComponent } from './requerimiento-to-solucion-detail.component';
import { RequerimientoToSolucionUpdateComponent } from './requerimiento-to-solucion-update.component';
import { RequerimientoToSolucionDeleteDialogComponent } from './requerimiento-to-solucion-delete-dialog.component';
import { requerimientoToSolucionRoute } from './requerimiento-to-solucion.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(requerimientoToSolucionRoute)],
  declarations: [
    RequerimientoToSolucionComponent,
    RequerimientoToSolucionDetailComponent,
    RequerimientoToSolucionUpdateComponent,
    RequerimientoToSolucionDeleteDialogComponent
  ],
  entryComponents: [RequerimientoToSolucionDeleteDialogComponent]
})
export class HometechclaimRequerimientoToSolucionModule {}
