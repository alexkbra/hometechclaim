import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IColaboradores, Colaboradores } from 'app/shared/model/colaboradores.model';
import { ColaboradoresService } from './colaboradores.service';
import { IDealer } from 'app/shared/model/dealer.model';
import { DealerService } from 'app/entities/dealer/dealer.service';

@Component({
  selector: 'jhi-colaboradores-update',
  templateUrl: './colaboradores-update.component.html'
})
export class ColaboradoresUpdateComponent implements OnInit {
  isSaving = false;
  dealers: IDealer[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    correo: [null, [Validators.minLength(5), Validators.maxLength(255)]],
    activo: [],
    idusuario: [],
    dealer: [null, Validators.required]
  });

  constructor(
    protected colaboradoresService: ColaboradoresService,
    protected dealerService: DealerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ colaboradores }) => {
      this.updateForm(colaboradores);

      this.dealerService.query().subscribe((res: HttpResponse<IDealer[]>) => (this.dealers = res.body || []));
    });
  }

  updateForm(colaboradores: IColaboradores): void {
    this.editForm.patchValue({
      id: colaboradores.id,
      nombre: colaboradores.nombre,
      correo: colaboradores.correo,
      activo: colaboradores.activo,
      idusuario: colaboradores.idusuario,
      dealer: colaboradores.dealer
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const colaboradores = this.createFromForm();
    if (colaboradores.id !== undefined) {
      this.subscribeToSaveResponse(this.colaboradoresService.update(colaboradores));
    } else {
      this.subscribeToSaveResponse(this.colaboradoresService.create(colaboradores));
    }
  }

  private createFromForm(): IColaboradores {
    return {
      ...new Colaboradores(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      correo: this.editForm.get(['correo'])!.value,
      activo: this.editForm.get(['activo'])!.value,
      idusuario: this.editForm.get(['idusuario'])!.value,
      dealer: this.editForm.get(['dealer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IColaboradores>>): void {
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
