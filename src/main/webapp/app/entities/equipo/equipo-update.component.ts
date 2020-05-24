import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEquipo, Equipo } from 'app/shared/model/equipo.model';
import { EquipoService } from './equipo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IMarca } from 'app/shared/model/marca.model';
import { MarcaService } from 'app/entities/marca/marca.service';

@Component({
  selector: 'jhi-equipo-update',
  templateUrl: './equipo-update.component.html'
})
export class EquipoUpdateComponent implements OnInit {
  isSaving = false;
  marcas: IMarca[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(250)]],
    version: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
    controlador: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    accountname: [null, [Validators.minLength(5), Validators.maxLength(100)]],
    controllermacaddress: [null, [Validators.minLength(5), Validators.maxLength(100)]],
    imagenUrl: [],
    imagenUrlContentType: [],
    valor: [null, [Validators.required]],
    marca: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected equipoService: EquipoService,
    protected marcaService: MarcaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipo }) => {
      this.updateForm(equipo);

      this.marcaService.query().subscribe((res: HttpResponse<IMarca[]>) => (this.marcas = res.body || []));
    });
  }

  updateForm(equipo: IEquipo): void {
    this.editForm.patchValue({
      id: equipo.id,
      nombre: equipo.nombre,
      version: equipo.version,
      controlador: equipo.controlador,
      accountname: equipo.accountname,
      controllermacaddress: equipo.controllermacaddress,
      imagenUrl: equipo.imagenUrl,
      imagenUrlContentType: equipo.imagenUrlContentType,
      valor: equipo.valor,
      marca: equipo.marca
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
    const equipo = this.createFromForm();
    if (equipo.id !== undefined) {
      this.subscribeToSaveResponse(this.equipoService.update(equipo));
    } else {
      this.subscribeToSaveResponse(this.equipoService.create(equipo));
    }
  }

  private createFromForm(): IEquipo {
    return {
      ...new Equipo(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      version: this.editForm.get(['version'])!.value,
      controlador: this.editForm.get(['controlador'])!.value,
      accountname: this.editForm.get(['accountname'])!.value,
      controllermacaddress: this.editForm.get(['controllermacaddress'])!.value,
      imagenUrlContentType: this.editForm.get(['imagenUrlContentType'])!.value,
      imagenUrl: this.editForm.get(['imagenUrl'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      marca: this.editForm.get(['marca'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipo>>): void {
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

  trackById(index: number, item: IMarca): any {
    return item.id;
  }
}
