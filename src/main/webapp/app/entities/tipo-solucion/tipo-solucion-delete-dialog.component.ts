import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoSolucion } from 'app/shared/model/tipo-solucion.model';
import { TipoSolucionService } from './tipo-solucion.service';

@Component({
  templateUrl: './tipo-solucion-delete-dialog.component.html'
})
export class TipoSolucionDeleteDialogComponent {
  tipoSolucion?: ITipoSolucion;

  constructor(
    protected tipoSolucionService: TipoSolucionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoSolucionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoSolucionListModification');
      this.activeModal.close();
    });
  }
}
