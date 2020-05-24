import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from './requerimiento.service';

@Component({
  templateUrl: './requerimiento-delete-dialog.component.html'
})
export class RequerimientoDeleteDialogComponent {
  requerimiento?: IRequerimiento;

  constructor(
    protected requerimientoService: RequerimientoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requerimientoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('requerimientoListModification');
      this.activeModal.close();
    });
  }
}
