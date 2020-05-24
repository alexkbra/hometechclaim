import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { SolucionComponent } from './solucion.component';
import { SolucionDetailComponent } from './solucion-detail.component';
import { SolucionUpdateComponent } from './solucion-update.component';
import { SolucionDeleteDialogComponent } from './solucion-delete-dialog.component';
import { solucionRoute } from './solucion.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(solucionRoute)],
  declarations: [SolucionComponent, SolucionDetailComponent, SolucionUpdateComponent, SolucionDeleteDialogComponent],
  entryComponents: [SolucionDeleteDialogComponent]
})
export class HometechclaimSolucionModule {}
