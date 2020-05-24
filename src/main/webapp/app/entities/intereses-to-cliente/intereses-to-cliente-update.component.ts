import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInteresesToCliente, InteresesToCliente } from 'app/shared/model/intereses-to-cliente.model';
import { InteresesToClienteService } from './intereses-to-cliente.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { IIntereses } from 'app/shared/model/intereses.model';
import { InteresesService } from 'app/entities/intereses/intereses.service';

type SelectableEntity = ICliente | IIntereses;

@Component({
  selector: 'jhi-intereses-to-cliente-update',
  templateUrl: './intereses-to-cliente-update.component.html'
})
export class InteresesToClienteUpdateComponent implements OnInit {
  isSaving = false;
  clientes: ICliente[] = [];
  intereses: IIntereses[] = [];

  editForm = this.fb.group({
    id: [],
    fechacreacion: [],
    cliente: [null, Validators.required],
    intereses: [null, Validators.required]
  });

  constructor(
    protected interesesToClienteService: InteresesToClienteService,
    protected clienteService: ClienteService,
    protected interesesService: InteresesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interesesToCliente }) => {
      if (!interesesToCliente.id) {
        const today = moment().startOf('day');
        interesesToCliente.fechacreacion = today;
      }

      this.updateForm(interesesToCliente);

      this.clienteService.query().subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body || []));

      this.interesesService.query().subscribe((res: HttpResponse<IIntereses[]>) => (this.intereses = res.body || []));
    });
  }

  updateForm(interesesToCliente: IInteresesToCliente): void {
    this.editForm.patchValue({
      id: interesesToCliente.id,
      fechacreacion: interesesToCliente.fechacreacion ? interesesToCliente.fechacreacion.format(DATE_TIME_FORMAT) : null,
      cliente: interesesToCliente.cliente,
      intereses: interesesToCliente.intereses
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const interesesToCliente = this.createFromForm();
    if (interesesToCliente.id !== undefined) {
      this.subscribeToSaveResponse(this.interesesToClienteService.update(interesesToCliente));
    } else {
      this.subscribeToSaveResponse(this.interesesToClienteService.create(interesesToCliente));
    }
  }

  private createFromForm(): IInteresesToCliente {
    return {
      ...new InteresesToCliente(),
      id: this.editForm.get(['id'])!.value,
      fechacreacion: this.editForm.get(['fechacreacion'])!.value
        ? moment(this.editForm.get(['fechacreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      cliente: this.editForm.get(['cliente'])!.value,
      intereses: this.editForm.get(['intereses'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInteresesToCliente>>): void {
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
