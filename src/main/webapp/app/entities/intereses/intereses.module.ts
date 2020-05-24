import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HometechclaimSharedModule } from 'app/shared/shared.module';
import { InteresesComponent } from './intereses.component';
import { InteresesDetailComponent } from './intereses-detail.component';
import { InteresesUpdateComponent } from './intereses-update.component';
import { InteresesDeleteDialogComponent } from './intereses-delete-dialog.component';
import { interesesRoute } from './intereses.route';

@NgModule({
  imports: [HometechclaimSharedModule, RouterModule.forChild(interesesRoute)],
  declarations: [InteresesComponent, InteresesDetailComponent, InteresesUpdateComponent, InteresesDeleteDialogComponent],
  entryComponents: [InteresesDeleteDialogComponent]
})
export class HometechclaimInteresesModule {}
