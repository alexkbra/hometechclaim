import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITipoSolucion, TipoSolucion } from 'app/shared/model/tipo-solucion.model';
import { TipoSolucionService } from './tipo-solucion.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-tipo-solucion-update',
  templateUrl: './tipo-solucion-update.component.html'
})
export class TipoSolucionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(250)]],
    code: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(10)]],
    imagenUrl: [],
    imagenUrlContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected tipoSolucionService: TipoSolucionService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoSolucion }) => {
      this.updateForm(tipoSolucion);
    });
  }

  updateForm(tipoSolucion: ITipoSolucion): void {
    this.editForm.patchValue({
      id: tipoSolucion.id,
      nombre: tipoSolucion.nombre,
      code: tipoSolucion.code,
      imagenUrl: tipoSolucion.imagenUrl,
      imagenUrlContentType: tipoSolucion.imagenUrlContentType
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoSolucion = this.createFromForm();
    if (tipoSolucion.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoSolucionService.update(tipoSolucion));
    } else {
      this.subscribeToSaveResponse(this.tipoSolucionService.create(tipoSolucion));
    }
  }

  private createFromForm(): ITipoSolucion {
    return {
      ...new TipoSolucion(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      code: this.editForm.get(['code'])!.value,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoSolucion>>): void {
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
