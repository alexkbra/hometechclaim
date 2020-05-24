import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDealer } from 'app/shared/model/dealer.model';
import { DealerService } from 'app/entities/dealer/dealer.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;
  dealers: IDealer[] = [];
  fechanacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    apellido: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    correo: [null, [Validators.minLength(5), Validators.maxLength(255)]],
    codigoDealer: [null, [Validators.minLength(5), Validators.maxLength(255)]],
    idciudad: [],
    telefonocelular: [null, [Validators.minLength(5), Validators.maxLength(45)]],
    telefonofijo: [null, [Validators.minLength(5), Validators.maxLength(45)]],
    telefonoempresarial: [null, [Validators.minLength(5), Validators.maxLength(45)]],
    direccionresidencial: [null, [Validators.minLength(5), Validators.maxLength(100)]],
    direccionempresarial: [null, [Validators.minLength(5), Validators.maxLength(100)]],
    fechanacimiento: [null, [Validators.required]],
    idusuario: [],
    imagen: [],
    imagenContentType: [],
    dealer: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected clienteService: ClienteService,
    protected dealerService: DealerService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);

      this.dealerService.query().subscribe((res: HttpResponse<IDealer[]>) => (this.dealers = res.body || []));
    });
  }

  updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      nombre: cliente.nombre,
      apellido: cliente.apellido,
      correo: cliente.correo,
      codigoDealer: cliente.codigoDealer,
      idciudad: cliente.idciudad,
      telefonocelular: cliente.telefonocelular,
      telefonofijo: cliente.telefonofijo,
      telefonoempresarial: cliente.telefonoempresarial,
      direccionresidencial: cliente.direccionresidencial,
      direccionempresarial: cliente.direccionempresarial,
      fechanacimiento: cliente.fechanacimiento,
      idusuario: cliente.idusuario,
      imagen: cliente.imagen,
      imagenContentType: cliente.imagenContentType,
      dealer: cliente.dealer
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
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellido: this.editForm.get(['apellido'])!.value,
      correo: this.editForm.get(['correo'])!.value,
      codigoDealer: this.editForm.get(['codigoDealer'])!.value,
      idciudad: this.editForm.get(['idciudad'])!.value,
      telefonocelular: this.editForm.get(['telefonocelular'])!.value,
      telefonofijo: this.editForm.get(['telefonofijo'])!.value,
      telefonoempresarial: this.editForm.get(['telefonoempresarial'])!.value,
      direccionresidencial: this.editForm.get(['direccionresidencial'])!.value,
      direccionempresarial: this.editForm.get(['direccionempresarial'])!.value,
      fechanacimiento: this.editForm.get(['fechanacimiento'])!.value,
      idusuario: this.editForm.get(['idusuario'])!.value,
      imagenContentType: this.editForm.get(['imagenContentType'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      dealer: this.editForm.get(['dealer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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

  trackById(index: number, item: IDealer): any {
    return item.id;
  }
}
