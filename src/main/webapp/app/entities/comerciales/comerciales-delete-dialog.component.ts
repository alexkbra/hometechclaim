import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComerciales } from 'app/shared/model/comerciales.model';
import { ComercialesService } from './comerciales.service';

@Component({
  templateUrl: './comerciales-delete-dialog.component.html'
})
export class ComercialesDeleteDialogComponent {
  comerciales?: IComerciales;

  constructor(
    protected comercialesService: ComercialesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comercialesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('comercialesListModification');
      this.activeModal.close();
    });
  }
}
