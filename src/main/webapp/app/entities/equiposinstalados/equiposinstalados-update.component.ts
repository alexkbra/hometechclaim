import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEquiposinstalados, Equiposinstalados } from 'app/shared/model/equiposinstalados.model';
import { EquiposinstaladosService } from './equiposinstalados.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { IEquipo } from 'app/shared/model/equipo.model';
import { EquipoService } from 'app/entities/equipo/equipo.service';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto/proyecto.service';

type SelectableEntity = ICliente | IEquipo | IProyecto;

@Component({
  selector: 'jhi-equiposinstalados-update',
  templateUrl: './equiposinstalados-update.component.html'
})
export class EquiposinstaladosUpdateComponent implements OnInit {
  isSaving = false;
  clientes: ICliente[] = [];
  equipos: IEquipo[] = [];
  proyectos: IProyecto[] = [];

  editForm = this.fb.group({
    id: [],
    descripcion: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(500)]],
    observacion: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(500)]],
    fechainstalacion: [],
    posibleactuliazcion: [],
    cantidad: [null, [Validators.required]],
    cliente: [null, Validators.required],
    equipo: [null, Validators.required],
    proyecto: [null, Validators.required]
  });

  constructor(
    protected equiposinstaladosService: EquiposinstaladosService,
    protected clienteService: ClienteService,
    protected equipoService: EquipoService,
    protected proyectoService: ProyectoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equiposinstalados }) => {
      if (!equiposinstalados.id) {
        const today = moment().startOf('day');
        equiposinstalados.fechainstalacion = today;
      }

      this.updateForm(equiposinstalados);

      this.clienteService.query().subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body || []));

      this.equipoService.query().subscribe((res: HttpResponse<IEquipo[]>) => (this.equipos = res.body || []));

      this.proyectoService.query().subscribe((res: HttpResponse<IProyecto[]>) => (this.proyectos = res.body || []));
    });
  }

  updateForm(equiposinstalados: IEquiposinstalados): void {
    this.editForm.patchValue({
      id: equiposinstalados.id,
      descripcion: equiposinstalados.descripcion,
      observacion: equiposinstalados.observacion,
      fechainstalacion: equiposinstalados.fechainstalacion ? equiposinstalados.fechainstalacion.format(DATE_TIME_FORMAT) : null,
      posibleactuliazcion: equiposinstalados.posibleactuliazcion,
      cantidad: equiposinstalados.cantidad,
      cliente: equiposinstalados.cliente,
      equipo: equiposinstalados.equipo,
      proyecto: equiposinstalados.proyecto
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equiposinstalados = this.createFromForm();
    if (equiposinstalados.id !== undefined) {
      this.subscribeToSaveResponse(this.equiposinstaladosService.update(equiposinstalados));
    } else {
      this.subscribeToSaveResponse(this.equiposinstaladosService.create(equiposinstalados));
    }
  }

  private createFromForm(): IEquiposinstalados {
    return {
      ...new Equiposinstalados(),
      id: this.editForm.get(['id'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      observacion: this.editForm.get(['observacion'])!.value,
      fechainstalacion: this.editForm.get(['fechainstalacion'])!.value
        ? moment(this.editForm.get(['fechainstalacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      posibleactuliazcion: this.editForm.get(['posibleactuliazcion'])!.value,
      cantidad: this.editForm.get(['cantidad'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
      equipo: this.editForm.get(['equipo'])!.value,
      proyecto: this.editForm.get(['proyecto'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquiposinstalados>>): void {
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
