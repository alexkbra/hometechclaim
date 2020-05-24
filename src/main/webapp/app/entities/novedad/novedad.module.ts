import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { NovedadComponent } from './novedad.component';
import { NovedadDetailComponent } from './novedad-detail.component';
import { NovedadUpdateComponent } from './novedad-update.component';
import { NovedadDeleteDialogComponent } from './novedad-delete-dialog.component';
import { novedadRoute } from './novedad.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(novedadRoute)],
  declarations: [NovedadComponent, NovedadDetailComponent, NovedadUpdateComponent, NovedadDeleteDialogComponent],
  entryComponents: [NovedadDeleteDialogComponent]
})
export class HometechclaimNovedadModule {}
