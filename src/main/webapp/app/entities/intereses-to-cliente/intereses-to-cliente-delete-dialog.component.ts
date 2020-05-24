import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInteresesToCliente } from 'app/shared/model/intereses-to-cliente.model';
import { InteresesToClienteService } from './intereses-to-cliente.service';

@Component({
  templateUrl: './intereses-to-cliente-delete-dialog.component.html'
})
export class InteresesToClienteDeleteDialogComponent {
  interesesToCliente?: IInteresesToCliente;

  constructor(
    protected interesesToClienteService: InteresesToClienteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.interesesToClienteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('interesesToClienteListModification');
      this.activeModal.close();
    });
  }
}
