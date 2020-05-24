import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';
import { RequerimientoToSolucionService } from './requerimiento-to-solucion.service';

@Component({
  templateUrl: './requerimiento-to-solucion-delete-dialog.component.html'
})
export class RequerimientoToSolucionDeleteDialogComponent {
  requerimientoToSolucion?: IRequerimientoToSolucion;

  constructor(
    protected requerimientoToSolucionService: RequerimientoToSolucionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requerimientoToSolucionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('requerimientoToSolucionListModification');
      this.activeModal.close();
    });
  }
}
