import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INovedadToCliente } from 'app/shared/model/novedad-to-cliente.model';
import { NovedadToClienteService } from './novedad-to-cliente.service';

@Component({
  templateUrl: './novedad-to-cliente-delete-dialog.component.html'
})
export class NovedadToClienteDeleteDialogComponent {
  novedadToCliente?: INovedadToCliente;

  constructor(
    protected novedadToClienteService: NovedadToClienteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.novedadToClienteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('novedadToClienteListModification');
      this.activeModal.close();
    });
  }
}
