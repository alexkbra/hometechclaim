import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INotificaciones, Notificaciones } from 'app/shared/model/notificaciones.model';
import { NotificacionesService } from './notificaciones.service';

@Component({
  selector: 'jhi-notificaciones-update',
  templateUrl: './notificaciones-update.component.html'
})
export class NotificacionesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    correo: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(255)]],
    area: [null, [Validators.required]],
    activo: [null, [Validators.required]],
    idusuario: []
  });

  constructor(protected notificacionesService: NotificacionesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notificaciones }) => {
      this.updateForm(notificaciones);
    });
  }

  updateForm(notificaciones: INotificaciones): void {
    this.editForm.patchValue({
      id: notificaciones.id,
      correo: notificaciones.correo,
      area: notificaciones.area,
      activo: notificaciones.activo,
      idusuario: notificaciones.idusuario
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notificaciones = this.createFromForm();
    if (notificaciones.id !== undefined) {
      this.subscribeToSaveResponse(this.notificacionesService.update(notificaciones));
    } else {
      this.subscribeToSaveResponse(this.notificacionesService.create(notificaciones));
    }
  }

  private createFromForm(): INotificaciones {
    return {
      ...new Notificaciones(),
      id: this.editForm.get(['id'])!.value,
      correo: this.editForm.get(['correo'])!.value,
      area: this.editForm.get(['area'])!.value,
      activo: this.editForm.get(['activo'])!.value,
      idusuario: this.editForm.get(['idusuario'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotificaciones>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
