import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INotificaciones } from 'app/shared/model/notificaciones.model';
import { NotificacionesService } from './notificaciones.service';

@Component({
  templateUrl: './notificaciones-delete-dialog.component.html'
})
export class NotificacionesDeleteDialogComponent {
  notificaciones?: INotificaciones;

  constructor(
    protected notificacionesService: NotificacionesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notificacionesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('notificacionesListModification');
      this.activeModal.close();
    });
  }
}
