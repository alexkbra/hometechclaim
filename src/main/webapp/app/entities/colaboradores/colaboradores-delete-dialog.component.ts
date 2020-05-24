import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IColaboradores } from 'app/shared/model/colaboradores.model';
import { ColaboradoresService } from './colaboradores.service';

@Component({
  templateUrl: './colaboradores-delete-dialog.component.html'
})
export class ColaboradoresDeleteDialogComponent {
  colaboradores?: IColaboradores;

  constructor(
    protected colaboradoresService: ColaboradoresService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.colaboradoresService.delete(id).subscribe(() => {
      this.eventManager.broadcast('colaboradoresListModification');
      this.activeModal.close();
    });
  }
}
