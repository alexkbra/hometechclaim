import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from './proyecto.service';

@Component({
  templateUrl: './proyecto-delete-dialog.component.html'
})
export class ProyectoDeleteDialogComponent {
  proyecto?: IProyecto;

  constructor(protected proyectoService: ProyectoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.proyectoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('proyectoListModification');
      this.activeModal.close();
    });
  }
}
