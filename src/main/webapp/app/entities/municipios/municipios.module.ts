import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { MunicipiosComponent } from './municipios.component';
import { MunicipiosDetailComponent } from './municipios-detail.component';
import { MunicipiosUpdateComponent } from './municipios-update.component';
import { MunicipiosDeleteDialogComponent } from './municipios-delete-dialog.component';
import { municipiosRoute } from './municipios.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(municipiosRoute)],
  declarations: [MunicipiosComponent, MunicipiosDetailComponent, MunicipiosUpdateComponent, MunicipiosDeleteDialogComponent],
  entryComponents: [MunicipiosDeleteDialogComponent]
})
export class HometechclaimMunicipiosModule {}
