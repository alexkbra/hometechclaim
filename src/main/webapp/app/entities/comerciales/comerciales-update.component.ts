import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IComerciales, Comerciales } from 'app/shared/model/comerciales.model';
import { ComercialesService } from './comerciales.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-comerciales-update',
  templateUrl: './comerciales-update.component.html'
})
export class ComercialesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(45)]],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    descripcion: [],
    correo: [null, [Validators.minLength(5), Validators.maxLength(255)]],
    zona: [null, [Validators.minLength(5), Validators.maxLength(255)]],
    idciudad: [],
    idusuario: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected comercialesService: ComercialesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comerciales }) => {
      this.updateForm(comerciales);
    });
  }

  updateForm(comerciales: IComerciales): void {
    this.editForm.patchValue({
      id: comerciales.id,
      codigo: comerciales.codigo,
      nombre: comerciales.nombre,
      descripcion: comerciales.descripcion,
      correo: comerciales.correo,
      zona: comerciales.zona,
      idciudad: comerciales.idciudad,
      idusuario: comerciales.idusuario
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('hometechclaimApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comerciales = this.createFromForm();
    if (comerciales.id !== undefined) {
      this.subscribeToSaveResponse(this.comercialesService.update(comerciales));
    } else {
      this.subscribeToSaveResponse(this.comercialesService.create(comerciales));
    }
  }

  private createFromForm(): IComerciales {
    return {
      ...new Comerciales(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      correo: this.editForm.get(['correo'])!.value,
      zona: this.editForm.get(['zona'])!.value,
      idciudad: this.editForm.get(['idciudad'])!.value,
      idusuario: this.editForm.get(['idusuario'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComerciales>>): void {
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
