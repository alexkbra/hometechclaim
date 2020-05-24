import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { EquiposinstaladosComponent } from './equiposinstalados.component';
import { EquiposinstaladosDetailComponent } from './equiposinstalados-detail.component';
import { EquiposinstaladosUpdateComponent } from './equiposinstalados-update.component';
import { EquiposinstaladosDeleteDialogComponent } from './equiposinstalados-delete-dialog.component';
import { equiposinstaladosRoute } from './equiposinstalados.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(equiposinstaladosRoute)],
  declarations: [
    EquiposinstaladosComponent,
    EquiposinstaladosDetailComponent,
    EquiposinstaladosUpdateComponent,
    EquiposinstaladosDeleteDialogComponent
  ],
  entryComponents: [EquiposinstaladosDeleteDialogComponent]
})
export class HometechclaimEquiposinstaladosModule {}
