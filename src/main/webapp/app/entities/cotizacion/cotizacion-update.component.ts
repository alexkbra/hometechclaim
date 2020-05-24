import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICotizacion, Cotizacion } from 'app/shared/model/cotizacion.model';
import { CotizacionService } from './cotizacion.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

@Component({
  selector: 'jhi-cotizacion-update',
  templateUrl: './cotizacion-update.component.html'
})
export class CotizacionUpdateComponent implements OnInit {
  isSaving = false;
  clientes: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    fechacreacion: [],
    descripcion: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    valoriva: [null, [Validators.required]],
    totalsiniva: [null, [Validators.required]],
    porcentajedescuento: [null, [Validators.required]],
    fechamantenimiento: [],
    activo: [],
    total: [null, [Validators.required]],
    estadocotizacion: [],
    cliente: [null, Validators.required]
  });

  constructor(
    protected cotizacionService: CotizacionService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cotizacion }) => {
      if (!cotizacion.id) {
        const today = moment().startOf('day');
        cotizacion.fechacreacion = today;
        cotizacion.fechamantenimiento = today;
      }

      this.updateForm(cotizacion);

      this.clienteService.query().subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body || []));
    });
  }

  updateForm(cotizacion: ICotizacion): void {
    this.editForm.patchValue({
      id: cotizacion.id,
      fechacreacion: cotizacion.fechacreacion ? cotizacion.fechacreacion.format(DATE_TIME_FORMAT) : null,
      descripcion: cotizacion.descripcion,
      valoriva: cotizacion.valoriva,
      totalsiniva: cotizacion.totalsiniva,
      porcentajedescuento: cotizacion.porcentajedescuento,
      fechamantenimiento: cotizacion.fechamantenimiento ? cotizacion.fechamantenimiento.format(DATE_TIME_FORMAT) : null,
      activo: cotizacion.activo,
      total: cotizacion.total,
      estadocotizacion: cotizacion.estadocotizacion,
      cliente: cotizacion.cliente
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cotizacion = this.createFromForm();
    if (cotizacion.id !== undefined) {
      this.subscribeToSaveResponse(this.cotizacionService.update(cotizacion));
    } else {
      this.subscribeToSaveResponse(this.cotizacionService.create(cotizacion));
    }
  }

  private createFromForm(): ICotizacion {
    return {
      ...new Cotizacion(),
      id: this.editForm.get(['id'])!.value,
      fechacreacion: this.editForm.get(['fechacreacion'])!.value
        ? moment(this.editForm.get(['fechacreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      descripcion: this.editForm.get(['descripcion'])!.value,
      valoriva: this.editForm.get(['valoriva'])!.value,
      totalsiniva: this.editForm.get(['totalsiniva'])!.value,
      porcentajedescuento: this.editForm.get(['porcentajedescuento'])!.value,
      fechamantenimiento: this.editForm.get(['fechamantenimiento'])!.value
        ? moment(this.editForm.get(['fechamantenimiento'])!.value, DATE_TIME_FORMAT)
        : undefined,
      activo: this.editForm.get(['activo'])!.value,
      total: this.editForm.get(['total'])!.value,
      estadocotizacion: this.editForm.get(['estadocotizacion'])!.value,
      cliente: this.editForm.get(['cliente'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICotizacion>>): void {
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

  trackById(index: number, item: ICliente): any {
    return item.id;
  }
}
