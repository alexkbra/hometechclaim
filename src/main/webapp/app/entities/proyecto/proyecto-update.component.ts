import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProyecto, Proyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from './proyecto.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-proyecto-update',
  templateUrl: './proyecto-update.component.html'
})
export class ProyectoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fechacreacion: [],
    descripcion: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    valoriva: [null, [Validators.required]],
    totalsiniva: [null, [Validators.required]],
    fechaultimomantenimiento: [],
    porcentajedescuento: [null, [Validators.required]],
    total: [null, [Validators.required]],
    activo: [],
    imagen: [],
    imagenContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected proyectoService: ProyectoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proyecto }) => {
      if (!proyecto.id) {
        const today = moment().startOf('day');
        proyecto.fechacreacion = today;
        proyecto.fechaultimomantenimiento = today;
      }

      this.updateForm(proyecto);
    });
  }

  updateForm(proyecto: IProyecto): void {
    this.editForm.patchValue({
      id: proyecto.id,
      fechacreacion: proyecto.fechacreacion ? proyecto.fechacreacion.format(DATE_TIME_FORMAT) : null,
      descripcion: proyecto.descripcion,
      valoriva: proyecto.valoriva,
      totalsiniva: proyecto.totalsiniva,
      fechaultimomantenimiento: proyecto.fechaultimomantenimiento ? proyecto.fechaultimomantenimiento.format(DATE_TIME_FORMAT) : null,
      porcentajedescuento: proyecto.porcentajedescuento,
      total: proyecto.total,
      activo: proyecto.activo,
      imagen: proyecto.imagen,
      imagenContentType: proyecto.imagenContentType
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
    const proyecto = this.createFromForm();
    if (proyecto.id !== undefined) {
      this.subscribeToSaveResponse(this.proyectoService.update(proyecto));
    } else {
      this.subscribeToSaveResponse(this.proyectoService.create(proyecto));
    }
  }

  private createFromForm(): IProyecto {
    return {
      ...new Proyecto(),
      id: this.editForm.get(['id'])!.value,
      fechacreacion: this.editForm.get(['fechacreacion'])!.value
        ? moment(this.editForm.get(['fechacreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      descripcion: this.editForm.get(['descripcion'])!.value,
      valoriva: this.editForm.get(['valoriva'])!.value,
      totalsiniva: this.editForm.get(['totalsiniva'])!.value,
      fechaultimomantenimiento: this.editForm.get(['fechaultimomantenimiento'])!.value
        ? moment(this.editForm.get(['fechaultimomantenimiento'])!.value, DATE_TIME_FORMAT)
        : undefined,
      porcentajedescuento: this.editForm.get(['porcentajedescuento'])!.value,
      total: this.editForm.get(['total'])!.value,
      activo: this.editForm.get(['activo'])!.value,
      imagenContentType: this.editForm.get(['imagenContentType'])!.value,
      imagen: this.editForm.get(['imagen'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProyecto>>): void {
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
