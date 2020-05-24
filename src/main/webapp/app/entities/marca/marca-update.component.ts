import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IMarca, Marca } from 'app/shared/model/marca.model';
import { MarcaService } from './marca.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITipoEquipo } from 'app/shared/model/tipo-equipo.model';
import { TipoEquipoService } from 'app/entities/tipo-equipo/tipo-equipo.service';

@Component({
  selector: 'jhi-marca-update',
  templateUrl: './marca-update.component.html'
})
export class MarcaUpdateComponent implements OnInit {
  isSaving = false;
  tipoequipos: ITipoEquipo[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    imagenUrl: [],
    imagenUrlContentType: [],
    code: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(10)]],
    tipoEquipo: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected marcaService: MarcaService,
    protected tipoEquipoService: TipoEquipoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ marca }) => {
      this.updateForm(marca);

      this.tipoEquipoService.query().subscribe((res: HttpResponse<ITipoEquipo[]>) => (this.tipoequipos = res.body || []));
    });
  }

  updateForm(marca: IMarca): void {
    this.editForm.patchValue({
      id: marca.id,
      nombre: marca.nombre,
      imagenUrl: marca.imagenUrl,
      imagenUrlContentType: marca.imagenUrlContentType,
      code: marca.code,
      tipoEquipo: marca.tipoEquipo
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
    const marca = this.createFromForm();
    if (marca.id !== undefined) {
      this.subscribeToSaveResponse(this.marcaService.update(marca));
    } else {
      this.subscribeToSaveResponse(this.marcaService.create(marca));
    }
  }

  private createFromForm(): IMarca {
    return {
      ...new Marca(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value,
      code: this.editForm.get(['code'])!.value,
      tipoEquipo: this.editForm.get(['tipoEquipo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMarca>>): void {
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

  trackById(index: number, item: ITipoEquipo): any {
    return item.id;
  }
}
