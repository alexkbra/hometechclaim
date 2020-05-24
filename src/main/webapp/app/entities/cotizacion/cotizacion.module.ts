import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { CotizacionComponent } from './cotizacion.component';
import { CotizacionDetailComponent } from './cotizacion-detail.component';
import { CotizacionUpdateComponent } from './cotizacion-update.component';
import { CotizacionDeleteDialogComponent } from './cotizacion-delete-dialog.component';
import { cotizacionRoute } from './cotizacion.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(cotizacionRoute)],
  declarations: [CotizacionComponent, CotizacionDetailComponent, CotizacionUpdateComponent, CotizacionDeleteDialogComponent],
  entryComponents: [CotizacionDeleteDialogComponent]
})
export class HometechclaimCotizacionModule {}
