import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';
import { EquiposcotizadosToEquiposService } from './equiposcotizados-to-equipos.service';

@Component({
  templateUrl: './equiposcotizados-to-equipos-delete-dialog.component.html'
})
export class EquiposcotizadosToEquiposDeleteDialogComponent {
  equiposcotizadosToEquipos?: IEquiposcotizadosToEquipos;

  constructor(
    protected equiposcotizadosToEquiposService: EquiposcotizadosToEquiposService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.equiposcotizadosToEquiposService.delete(id).subscribe(() => {
      this.eventManager.broadcast('equiposcotizadosToEquiposListModification');
      this.activeModal.close();
    });
  }
}
