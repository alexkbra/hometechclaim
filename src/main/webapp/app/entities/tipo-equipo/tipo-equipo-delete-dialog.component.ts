import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoEquipo } from 'app/shared/model/tipo-equipo.model';
import { TipoEquipoService } from './tipo-equipo.service';

@Component({
  templateUrl: './tipo-equipo-delete-dialog.component.html'
})
export class TipoEquipoDeleteDialogComponent {
  tipoEquipo?: ITipoEquipo;

  constructor(
    protected tipoEquipoService: TipoEquipoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoEquipoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoEquipoListModification');
      this.activeModal.close();
    });
  }
}
