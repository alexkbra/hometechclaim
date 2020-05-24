import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INovedadToCliente, NovedadToCliente } from 'app/shared/model/novedad-to-cliente.model';
import { NovedadToClienteService } from './novedad-to-cliente.service';
import { INovedad } from 'app/shared/model/novedad.model';
import { NovedadService } from 'app/entities/novedad/novedad.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

type SelectableEntity = INovedad | ICliente;

@Component({
  selector: 'jhi-novedad-to-cliente-update',
  templateUrl: './novedad-to-cliente-update.component.html'
})
export class NovedadToClienteUpdateComponent implements OnInit {
  isSaving = false;
  novedads: INovedad[] = [];
  clientes: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    fechacreacion: [],
    novedad: [null, Validators.required],
    cliente: [null, Validators.required]
  });

  constructor(
    protected novedadToClienteService: NovedadToClienteService,
    protected novedadService: NovedadService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ novedadToCliente }) => {
      if (!novedadToCliente.id) {
        const today = moment().startOf('day');
        novedadToCliente.fechacreacion = today;
      }

      this.updateForm(novedadToCliente);

      this.novedadService.query().subscribe((res: HttpResponse<INovedad[]>) => (this.novedads = res.body || []));

      this.clienteService.query().subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body || []));
    });
  }

  updateForm(novedadToCliente: INovedadToCliente): void {
    this.editForm.patchValue({
      id: novedadToCliente.id,
      fechacreacion: novedadToCliente.fechacreacion ? novedadToCliente.fechacreacion.format(DATE_TIME_FORMAT) : null,
      novedad: novedadToCliente.novedad,
      cliente: novedadToCliente.cliente
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const novedadToCliente = this.createFromForm();
    if (novedadToCliente.id !== undefined) {
      this.subscribeToSaveResponse(this.novedadToClienteService.update(novedadToCliente));
    } else {
      this.subscribeToSaveResponse(this.novedadToClienteService.create(novedadToCliente));
    }
  }

  private createFromForm(): INovedadToCliente {
    return {
      ...new NovedadToCliente(),
      id: this.editForm.get(['id'])!.value,
      fechacreacion: this.editForm.get(['fechacreacion'])!.value
        ? moment(this.editForm.get(['fechacreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      novedad: this.editForm.get(['novedad'])!.value,
      cliente: this.editForm.get(['cliente'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INovedadToCliente>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
