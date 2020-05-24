import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';

@Component({
  selector: 'jhi-equiposcotizados-to-equipos-detail',
  templateUrl: './equiposcotizados-to-equipos-detail.component.html'
})
export class EquiposcotizadosToEquiposDetailComponent implements OnInit {
  equiposcotizadosToEquipos: IEquiposcotizadosToEquipos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equiposcotizadosToEquipos }) => (this.equiposcotizadosToEquipos = equiposcotizadosToEquipos));
  }

  previousState(): void {
    window.history.back();
  }
}
