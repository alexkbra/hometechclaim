import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IRequerimiento, Requerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from './requerimiento.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-requerimiento-update',
  templateUrl: './requerimiento-update.component.html'
})
export class RequerimientoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    detalleproblema: [null, [Validators.required]],
    fechacreacion: [null, [Validators.required]],
    fechaactualizacion: [],
    observaciones: [],
    idusuarioatendio: [],
    idusuarioactualizo: [],
    idUsuario: [],
    imagen: [],
    imagenContentType: [],
    estadoRequerimiento: [],
    tipoRequerimiento: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected requerimientoService: RequerimientoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requerimiento }) => {
      if (!requerimiento.id) {
        const today = moment().startOf('day');
        requerimiento.fechacreacion = today;
        requerimiento.fechaactualizacion = today;
      }

      this.updateForm(requerimiento);
    });
  }

  updateForm(requerimiento: IRequerimiento): void {
    this.editForm.patchValue({
      id: requerimiento.id,
      detalleproblema: requerimiento.detalleproblema,
      fechacreacion: requerimiento.fechacreacion ? requerimiento.fechacreacion.format(DATE_TIME_FORMAT) : null,
      fechaactualizacion: requerimiento.fechaactualizacion ? requerimiento.fechaactualizacion.format(DATE_TIME_FORMAT) : null,
      observaciones: requerimiento.observaciones,
      idusuarioatendio: requerimiento.idusuarioatendio,
      idusuarioactualizo: requerimiento.idusuarioactualizo,
      idUsuario: requerimiento.idUsuario,
      imagen: requerimiento.imagen,
      imagenContentType: requerimiento.imagenContentType,
      estadoRequerimiento: requerimiento.estadoRequerimiento,
      tipoRequerimiento: requerimiento.tipoRequerimiento
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
    const requerimiento = this.createFromForm();
    if (requerimiento.id !== undefined) {
      this.subscribeToSaveResponse(this.requerimientoService.update(requerimiento));
    } else {
      this.subscribeToSaveResponse(this.requerimientoService.create(requerimiento));
    }
  }

  private createFromForm(): IRequerimiento {
    return {
      ...new Requerimiento(),
      id: this.editForm.get(['id'])!.value,
      detalleproblema: this.editForm.get(['detalleproblema'])!.value,
      fechacreacion: this.editForm.get(['fechacreacion'])!.value
        ? moment(this.editForm.get(['fechacreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaactualizacion: this.editForm.get(['fechaactualizacion'])!.value
        ? moment(this.editForm.get(['fechaactualizacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      observaciones: this.editForm.get(['observaciones'])!.value,
      idusuarioatendio: this.editForm.get(['idusuarioatendio'])!.value,
      idusuarioactualizo: this.editForm.get(['idusuarioactualizo'])!.value,
      idUsuario: this.editForm.get(['idUsuario'])!.value,
      imagenContentType: this.editForm.get(['imagenContentType'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      estadoRequerimiento: this.editForm.get(['estadoRequerimiento'])!.value,
      tipoRequerimiento: this.editForm.get(['tipoRequerimiento'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequerimiento>>): void {
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
