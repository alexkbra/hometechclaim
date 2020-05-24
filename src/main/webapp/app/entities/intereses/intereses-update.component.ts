import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIntereses, Intereses } from 'app/shared/model/intereses.model';
import { InteresesService } from './intereses.service';

@Component({
  selector: 'jhi-intereses-update',
  templateUrl: './intereses-update.component.html'
})
export class InteresesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(45)]],
    descripcion: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(45)]],
    imagen: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(45)]],
    code: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(10)]]
  });

  constructor(protected interesesService: InteresesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ intereses }) => {
      this.updateForm(intereses);
    });
  }

  updateForm(intereses: IIntereses): void {
    this.editForm.patchValue({
      id: intereses.id,
      nombre: intereses.nombre,
      descripcion: intereses.descripcion,
      imagen: intereses.imagen,
      code: intereses.code
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const intereses = this.createFromForm();
    if (intereses.id !== undefined) {
      this.subscribeToSaveResponse(this.interesesService.update(intereses));
    } else {
      this.subscribeToSaveResponse(this.interesesService.create(intereses));
    }
  }

  private createFromForm(): IIntereses {
    return {
      ...new Intereses(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      code: this.editForm.get(['code'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntereses>>): void {
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
