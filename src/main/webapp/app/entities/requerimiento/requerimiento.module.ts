import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { RequerimientoComponent } from './requerimiento.component';
import { RequerimientoDetailComponent } from './requerimiento-detail.component';
import { RequerimientoUpdateComponent } from './requerimiento-update.component';
import { RequerimientoDeleteDialogComponent } from './requerimiento-delete-dialog.component';
import { requerimientoRoute } from './requerimiento.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(requerimientoRoute)],
  declarations: [RequerimientoComponent, RequerimientoDetailComponent, RequerimientoUpdateComponent, RequerimientoDeleteDialogComponent],
  entryComponents: [RequerimientoDeleteDialogComponent]
})
export class HometechclaimRequerimientoModule {}
