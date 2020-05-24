import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { ColaboradoresComponent } from './colaboradores.component';
import { ColaboradoresDetailComponent } from './colaboradores-detail.component';
import { ColaboradoresUpdateComponent } from './colaboradores-update.component';
import { ColaboradoresDeleteDialogComponent } from './colaboradores-delete-dialog.component';
import { colaboradoresRoute } from './colaboradores.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(colaboradoresRoute)],
  declarations: [ColaboradoresComponent, ColaboradoresDetailComponent, ColaboradoresUpdateComponent, ColaboradoresDeleteDialogComponent],
  entryComponents: [ColaboradoresDeleteDialogComponent]
})
export class HometechclaimColaboradoresModule {}
