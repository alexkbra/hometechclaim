import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITipoEquipo, TipoEquipo } from 'app/shared/model/tipo-equipo.model';
import { TipoEquipoService } from './tipo-equipo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ISolucion } from 'app/shared/model/solucion.model';
import { SolucionService } from 'app/entities/solucion/solucion.service';

@Component({
  selector: 'jhi-tipo-equipo-update',
  templateUrl: './tipo-equipo-update.component.html'
})
export class TipoEquipoUpdateComponent implements OnInit {
  isSaving = false;
  solucions: ISolucion[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    descripcion: [],
    imagenUrl: [],
    imagenUrlContentType: [],
    code: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(10)]],
    solucion: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected tipoEquipoService: TipoEquipoService,
    protected solucionService: SolucionService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoEquipo }) => {
      this.updateForm(tipoEquipo);

      this.solucionService.query().subscribe((res: HttpResponse<ISolucion[]>) => (this.solucions = res.body || []));
    });
  }

  updateForm(tipoEquipo: ITipoEquipo): void {
    this.editForm.patchValue({
      id: tipoEquipo.id,
      nombre: tipoEquipo.nombre,
      descripcion: tipoEquipo.descripcion,
      imagenUrl: tipoEquipo.imagenUrl,
      imagenUrlContentType: tipoEquipo.imagenUrlContentType,
      code: tipoEquipo.code,
      solucion: tipoEquipo.solucion
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
    const tipoEquipo = this.createFromForm();
    if (tipoEquipo.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoEquipoService.update(tipoEquipo));
    } else {
      this.subscribeToSaveResponse(this.tipoEquipoService.create(tipoEquipo));
    }
  }

  private createFromForm(): ITipoEquipo {
    return {
      ...new TipoEquipo(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value,
      code: this.editForm.get(['code'])!.value,
      solucion: this.editForm.get(['solucion'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoEquipo>>): void {
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

  trackById(index: number, item: ISolucion): any {
    return item.id;
  }
}
