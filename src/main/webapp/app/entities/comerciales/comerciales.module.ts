import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { ComercialesComponent } from './comerciales.component';
import { ComercialesDetailComponent } from './comerciales-detail.component';
import { ComercialesUpdateComponent } from './comerciales-update.component';
import { ComercialesDeleteDialogComponent } from './comerciales-delete-dialog.component';
import { comercialesRoute } from './comerciales.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(comercialesRoute)],
  declarations: [ComercialesComponent, ComercialesDetailComponent, ComercialesUpdateComponent, ComercialesDeleteDialogComponent],
  entryComponents: [ComercialesDeleteDialogComponent]
})
export class HometechclaimComercialesModule {}
