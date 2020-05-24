import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { EquiposcotizadosToEquiposComponent } from './equiposcotizados-to-equipos.component';
import { EquiposcotizadosToEquiposDetailComponent } from './equiposcotizados-to-equipos-detail.component';
import { EquiposcotizadosToEquiposUpdateComponent } from './equiposcotizados-to-equipos-update.component';
import { EquiposcotizadosToEquiposDeleteDialogComponent } from './equiposcotizados-to-equipos-delete-dialog.component';
import { equiposcotizadosToEquiposRoute } from './equiposcotizados-to-equipos.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(equiposcotizadosToEquiposRoute)],
  declarations: [
    EquiposcotizadosToEquiposComponent,
    EquiposcotizadosToEquiposDetailComponent,
    EquiposcotizadosToEquiposUpdateComponent,
    EquiposcotizadosToEquiposDeleteDialogComponent
  ],
  entryComponents: [EquiposcotizadosToEquiposDeleteDialogComponent]
})
export class HometechclaimEquiposcotizadosToEquiposModule {}
