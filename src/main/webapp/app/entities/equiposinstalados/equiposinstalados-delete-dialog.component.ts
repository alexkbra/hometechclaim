import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquiposinstalados } from 'app/shared/model/equiposinstalados.model';
import { EquiposinstaladosService } from './equiposinstalados.service';

@Component({
  templateUrl: './equiposinstalados-delete-dialog.component.html'
})
export class EquiposinstaladosDeleteDialogComponent {
  equiposinstalados?: IEquiposinstalados;

  constructor(
    protected equiposinstaladosService: EquiposinstaladosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.equiposinstaladosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('equiposinstaladosListModification');
      this.activeModal.close();
    });
  }
}
