import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { InteresesToClienteComponent } from './intereses-to-cliente.component';
import { InteresesToClienteDetailComponent } from './intereses-to-cliente-detail.component';
import { InteresesToClienteUpdateComponent } from './intereses-to-cliente-update.component';
import { InteresesToClienteDeleteDialogComponent } from './intereses-to-cliente-delete-dialog.component';
import { interesesToClienteRoute } from './intereses-to-cliente.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(interesesToClienteRoute)],
  declarations: [
    InteresesToClienteComponent,
    InteresesToClienteDetailComponent,
    InteresesToClienteUpdateComponent,
    InteresesToClienteDeleteDialogComponent
  ],
  entryComponents: [InteresesToClienteDeleteDialogComponent]
})
export class HometechclaimInteresesToClienteModule {}
