import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INovedad } from 'app/shared/model/novedad.model';
import { NovedadService } from './novedad.service';

@Component({
  templateUrl: './novedad-delete-dialog.component.html'
})
export class NovedadDeleteDialogComponent {
  novedad?: INovedad;

  constructor(protected novedadService: NovedadService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.novedadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('novedadListModification');
      this.activeModal.close();
    });
  }
}
