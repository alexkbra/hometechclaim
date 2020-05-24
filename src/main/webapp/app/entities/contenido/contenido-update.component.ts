import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IContenido, Contenido } from 'app/shared/model/contenido.model';
import { ContenidoService } from './contenido.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { INovedad } from 'app/shared/model/novedad.model';
import { NovedadService } from 'app/entities/novedad/novedad.service';

@Component({
  selector: 'jhi-contenido-update',
  templateUrl: './contenido-update.component.html'
})
export class ContenidoUpdateComponent implements OnInit {
  isSaving = false;
  novedads: INovedad[] = [];

  editForm = this.fb.group({
    id: [],
    descripcion: [null, [Validators.required]],
    videoUrl: [],
    imagenURl: [],
    imagenURlContentType: [],
    audio: [],
    tipoContenido: [],
    activo: [],
    fechacreacion: [],
    novedad: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected contenidoService: ContenidoService,
    protected novedadService: NovedadService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contenido }) => {
      if (!contenido.id) {
        const today = moment().startOf('day');
        contenido.fechacreacion = today;
      }

      this.updateForm(contenido);

      this.novedadService.query().subscribe((res: HttpResponse<INovedad[]>) => (this.novedads = res.body || []));
    });
  }

  updateForm(contenido: IContenido): void {
    this.editForm.patchValue({
      id: contenido.id,
      descripcion: contenido.descripcion,
      videoUrl: contenido.videoUrl,
      imagenURl: contenido.imagenURl,
      imagenURlContentType: contenido.imagenURlContentType,
      audio: contenido.audio,
      tipoContenido: contenido.tipoContenido,
      activo: contenido.activo,
      fechacreacion: contenido.fechacreacion ? contenido.fechacreacion.format(DATE_TIME_FORMAT) : null,
      novedad: contenido.novedad
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
    const contenido = this.createFromForm();
    if (contenido.id !== undefined) {
      this.subscribeToSaveResponse(this.contenidoService.update(contenido));
    } else {
      this.subscribeToSaveResponse(this.contenidoService.create(contenido));
    }
  }

  private createFromForm(): IContenido {
    return {
      ...new Contenido(),
      id: this.editForm.get(['id'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      videoUrl: this.editForm.get(['videoUrl'])!.value,
      imagenURlContentType: this.editForm.get(['imagenURlContentType'])!.value,
      imagenURl: this.editForm.get(['imagenURl'])!.value,
      audio: this.editForm.get(['audio'])!.value,
      tipoContenido: this.editForm.get(['tipoContenido'])!.value,
      activo: this.editForm.get(['activo'])!.value,
      fechacreacion: this.editForm.get(['fechacreacion'])!.value
        ? moment(this.editForm.get(['fechacreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      novedad: this.editForm.get(['novedad'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContenido>>): void {
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

  trackById(index: number, item: INovedad): any {
    return item.id;
  }
}
