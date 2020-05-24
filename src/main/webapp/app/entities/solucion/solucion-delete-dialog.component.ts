import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISolucion } from 'app/shared/model/solucion.model';
import { SolucionService } from './solucion.service';

@Component({
  templateUrl: './solucion-delete-dialog.component.html'
})
export class SolucionDeleteDialogComponent {
  solucion?: ISolucion;

  constructor(protected solucionService: SolucionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.solucionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('solucionListModification');
      this.activeModal.close();
    });
  }
}
