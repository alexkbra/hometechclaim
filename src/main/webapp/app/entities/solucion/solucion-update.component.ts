import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ISolucion, Solucion } from 'app/shared/model/solucion.model';
import { SolucionService } from './solucion.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITipoSolucion } from 'app/shared/model/tipo-solucion.model';
import { TipoSolucionService } from 'app/entities/tipo-solucion/tipo-solucion.service';

@Component({
  selector: 'jhi-solucion-update',
  templateUrl: './solucion-update.component.html'
})
export class SolucionUpdateComponent implements OnInit {
  isSaving = false;
  tiposolucions: ITipoSolucion[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(250)]],
    descripcion: [],
    code: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(10)]],
    imagenUrl: [],
    imagenUrlContentType: [],
    tipoSolucion: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected solucionService: SolucionService,
    protected tipoSolucionService: TipoSolucionService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ solucion }) => {
      this.updateForm(solucion);

      this.tipoSolucionService.query().subscribe((res: HttpResponse<ITipoSolucion[]>) => (this.tiposolucions = res.body || []));
    });
  }

  updateForm(solucion: ISolucion): void {
    this.editForm.patchValue({
      id: solucion.id,
      nombre: solucion.nombre,
      descripcion: solucion.descripcion,
      code: solucion.code,
      imagenUrl: solucion.imagenUrl,
      imagenUrlContentType: solucion.imagenUrlContentType,
      tipoSolucion: solucion.tipoSolucion
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
    const solucion = this.createFromForm();
    if (solucion.id !== undefined) {
      this.subscribeToSaveResponse(this.solucionService.update(solucion));
    } else {
      this.subscribeToSaveResponse(this.solucionService.create(solucion));
    }
  }

  private createFromForm(): ISolucion {
    return {
      ...new Solucion(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      code: this.editForm.get(['code'])!.value,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value,
      tipoSolucion: this.editForm.get(['tipoSolucion'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISolucion>>): void {
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

  trackById(index: number, item: ITipoSolucion): any {
    return item.id;
  }
}
