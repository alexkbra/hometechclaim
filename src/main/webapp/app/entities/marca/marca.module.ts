import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { MarcaComponent } from './marca.component';
import { MarcaDetailComponent } from './marca-detail.component';
import { MarcaUpdateComponent } from './marca-update.component';
import { MarcaDeleteDialogComponent } from './marca-delete-dialog.component';
import { marcaRoute } from './marca.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(marcaRoute)],
  declarations: [MarcaComponent, MarcaDetailComponent, MarcaUpdateComponent, MarcaDeleteDialogComponent],
  entryComponents: [MarcaDeleteDialogComponent]
})
export class HometechclaimMarcaModule {}
