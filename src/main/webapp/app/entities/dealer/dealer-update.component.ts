import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDealer, Dealer } from 'app/shared/model/dealer.model';
import { DealerService } from './dealer.service';
import { IComerciales } from 'app/shared/model/comerciales.model';
import { ComercialesService } from 'app/entities/comerciales/comerciales.service';

@Component({
  selector: 'jhi-dealer-update',
  templateUrl: './dealer-update.component.html'
})
export class DealerUpdateComponent implements OnInit {
  isSaving = false;
  comerciales: IComerciales[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    correo: [null, [Validators.minLength(5), Validators.maxLength(255)]],
    codigo: [null, [Validators.minLength(5), Validators.maxLength(255)]],
    idciudad: [],
    direccion: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    telefonofijo: [null, [Validators.minLength(5), Validators.maxLength(45)]],
    telefonocelular: [null, [Validators.minLength(5), Validators.maxLength(45)]],
    idusuario: [],
    comerciales: [null, Validators.required]
  });

  constructor(
    protected dealerService: DealerService,
    protected comercialesService: ComercialesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dealer }) => {
      this.updateForm(dealer);

      this.comercialesService.query().subscribe((res: HttpResponse<IComerciales[]>) => (this.comerciales = res.body || []));
    });
  }

  updateForm(dealer: IDealer): void {
    this.editForm.patchValue({
      id: dealer.id,
      nombre: dealer.nombre,
      correo: dealer.correo,
      codigo: dealer.codigo,
      idciudad: dealer.idciudad,
      direccion: dealer.direccion,
      telefonofijo: dealer.telefonofijo,
      telefonocelular: dealer.telefonocelular,
      idusuario: dealer.idusuario,
      comerciales: dealer.comerciales
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dealer = this.createFromForm();
    if (dealer.id !== undefined) {
      this.subscribeToSaveResponse(this.dealerService.update(dealer));
    } else {
      this.subscribeToSaveResponse(this.dealerService.create(dealer));
    }
  }

  private createFromForm(): IDealer {
    return {
      ...new Dealer(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      correo: this.editForm.get(['correo'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      idciudad: this.editForm.get(['idciudad'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      telefonofijo: this.editForm.get(['telefonofijo'])!.value,
      telefonocelular: this.editForm.get(['telefonocelular'])!.value,
      idusuario: this.editForm.get(['idusuario'])!.value,
      comerciales: this.editForm.get(['comerciales'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDealer>>): void {
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

  trackById(index: number, item: IComerciales): any {
    return item.id;
  }
}
