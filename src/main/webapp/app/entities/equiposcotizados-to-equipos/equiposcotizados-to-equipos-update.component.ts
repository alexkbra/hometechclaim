import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEquiposcotizadosToEquipos, EquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';
import { EquiposcotizadosToEquiposService } from './equiposcotizados-to-equipos.service';
import { ICotizacion } from 'app/shared/model/cotizacion.model';
import { CotizacionService } from 'app/entities/cotizacion/cotizacion.service';
import { IEquipo } from 'app/shared/model/equipo.model';
import { EquipoService } from 'app/entities/equipo/equipo.service';

type SelectableEntity = ICotizacion | IEquipo;

@Component({
  selector: 'jhi-equiposcotizados-to-equipos-update',
  templateUrl: './equiposcotizados-to-equipos-update.component.html'
})
export class EquiposcotizadosToEquiposUpdateComponent implements OnInit {
  isSaving = false;
  cotizacions: ICotizacion[] = [];
  equipos: IEquipo[] = [];

  editForm = this.fb.group({
    id: [],
    valorUnidad: [null, [Validators.required]],
    valorUtilUnidad: [null, [Validators.required]],
    descuentoUnidad: [null, [Validators.required]],
    fechacotizacion: [null, [Validators.required]],
    cantidadEquipos: [null, [Validators.required]],
    cotizacion: [null, Validators.required],
    equiposcotizadosToEquipos: [null, Validators.required]
  });

  constructor(
    protected equiposcotizadosToEquiposService: EquiposcotizadosToEquiposService,
    protected cotizacionService: CotizacionService,
    protected equipoService: EquipoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equiposcotizadosToEquipos }) => {
      if (!equiposcotizadosToEquipos.id) {
        const today = moment().startOf('day');
        equiposcotizadosToEquipos.fechacotizacion = today;
      }

      this.updateForm(equiposcotizadosToEquipos);

      this.cotizacionService.query().subscribe((res: HttpResponse<ICotizacion[]>) => (this.cotizacions = res.body || []));

      this.equipoService.query().subscribe((res: HttpResponse<IEquipo[]>) => (this.equipos = res.body || []));
    });
  }

  updateForm(equiposcotizadosToEquipos: IEquiposcotizadosToEquipos): void {
    this.editForm.patchValue({
      id: equiposcotizadosToEquipos.id,
      valorUnidad: equiposcotizadosToEquipos.valorUnidad,
      valorUtilUnidad: equiposcotizadosToEquipos.valorUtilUnidad,
      descuentoUnidad: equiposcotizadosToEquipos.descuentoUnidad,
      fechacotizacion: equiposcotizadosToEquipos.fechacotizacion
        ? equiposcotizadosToEquipos.fechacotizacion.format(DATE_TIME_FORMAT)
        : null,
      cantidadEquipos: equiposcotizadosToEquipos.cantidadEquipos,
      cotizacion: equiposcotizadosToEquipos.cotizacion,
      equiposcotizadosToEquipos: equiposcotizadosToEquipos.equiposcotizadosToEquipos
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equiposcotizadosToEquipos = this.createFromForm();
    if (equiposcotizadosToEquipos.id !== undefined) {
      this.subscribeToSaveResponse(this.equiposcotizadosToEquiposService.update(equiposcotizadosToEquipos));
    } else {
      this.subscribeToSaveResponse(this.equiposcotizadosToEquiposService.create(equiposcotizadosToEquipos));
    }
  }

  private createFromForm(): IEquiposcotizadosToEquipos {
    return {
      ...new EquiposcotizadosToEquipos(),
      id: this.editForm.get(['id'])!.value,
      valorUnidad: this.editForm.get(['valorUnidad'])!.value,
      valorUtilUnidad: this.editForm.get(['valorUtilUnidad'])!.value,
      descuentoUnidad: this.editForm.get(['descuentoUnidad'])!.value,
      fechacotizacion: this.editForm.get(['fechacotizacion'])!.value
        ? moment(this.editForm.get(['fechacotizacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      cantidadEquipos: this.editForm.get(['cantidadEquipos'])!.value,
      cotizacion: this.editForm.get(['cotizacion'])!.value,
      equiposcotizadosToEquipos: this.editForm.get(['equiposcotizadosToEquipos'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquiposcotizadosToEquipos>>): void {
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
