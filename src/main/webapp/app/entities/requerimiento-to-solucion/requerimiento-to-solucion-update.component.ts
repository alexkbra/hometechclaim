import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRequerimientoToSolucion, RequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';
import { RequerimientoToSolucionService } from './requerimiento-to-solucion.service';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from 'app/entities/requerimiento/requerimiento.service';
import { ISolucion } from 'app/shared/model/solucion.model';
import { SolucionService } from 'app/entities/solucion/solucion.service';

type SelectableEntity = IRequerimiento | ISolucion;

@Component({
  selector: 'jhi-requerimiento-to-solucion-update',
  templateUrl: './requerimiento-to-solucion-update.component.html'
})
export class RequerimientoToSolucionUpdateComponent implements OnInit {
  isSaving = false;
  requerimientos: IRequerimiento[] = [];
  solucions: ISolucion[] = [];

  editForm = this.fb.group({
    id: [],
    fechacreacion: [null, [Validators.required]],
    requerimiento: [null, Validators.required],
    solucion: [null, Validators.required]
  });

  constructor(
    protected requerimientoToSolucionService: RequerimientoToSolucionService,
    protected requerimientoService: RequerimientoService,
    protected solucionService: SolucionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requerimientoToSolucion }) => {
      if (!requerimientoToSolucion.id) {
        const today = moment().startOf('day');
        requerimientoToSolucion.fechacreacion = today;
      }

      this.updateForm(requerimientoToSolucion);

      this.requerimientoService.query().subscribe((res: HttpResponse<IRequerimiento[]>) => (this.requerimientos = res.body || []));

      this.solucionService.query().subscribe((res: HttpResponse<ISolucion[]>) => (this.solucions = res.body || []));
    });
  }

  updateForm(requerimientoToSolucion: IRequerimientoToSolucion): void {
    this.editForm.patchValue({
      id: requerimientoToSolucion.id,
      fechacreacion: requerimientoToSolucion.fechacreacion ? requerimientoToSolucion.fechacreacion.format(DATE_TIME_FORMAT) : null,
      requerimiento: requerimientoToSolucion.requerimiento,
      solucion: requerimientoToSolucion.solucion
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requerimientoToSolucion = this.createFromForm();
    if (requerimientoToSolucion.id !== undefined) {
      this.subscribeToSaveResponse(this.requerimientoToSolucionService.update(requerimientoToSolucion));
    } else {
      this.subscribeToSaveResponse(this.requerimientoToSolucionService.create(requerimientoToSolucion));
    }
  }

  private createFromForm(): IRequerimientoToSolucion {
    return {
      ...new RequerimientoToSolucion(),
      id: this.editForm.get(['id'])!.value,
      fechacreacion: this.editForm.get(['fechacreacion'])!.value
        ? moment(this.editForm.get(['fechacreacion'])!.value, DATE_TIME_FORMAT)
        : undefined,
      requerimiento: this.editForm.get(['requerimiento'])!.value,
      solucion: this.editForm.get(['solucion'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequerimientoToSolucion>>): void {
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
