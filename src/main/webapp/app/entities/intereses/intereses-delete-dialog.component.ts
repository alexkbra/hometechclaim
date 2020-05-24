import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIntereses } from 'app/shared/model/intereses.model';
import { InteresesService } from './intereses.service';

@Component({
  templateUrl: './intereses-delete-dialog.component.html'
})
export class InteresesDeleteDialogComponent {
  intereses?: IIntereses;

  constructor(protected interesesService: InteresesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.interesesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('interesesListModification');
      this.activeModal.close();
    });
  }
}
