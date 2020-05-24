import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICotizacion } from 'app/shared/model/cotizacion.model';
import { CotizacionService } from './cotizacion.service';

@Component({
  templateUrl: './cotizacion-delete-dialog.component.html'
})
export class CotizacionDeleteDialogComponent {
  cotizacion?: ICotizacion;

  constructor(
    protected cotizacionService: CotizacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cotizacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cotizacionListModification');
      this.activeModal.close();
    });
  }
}
