import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEquiposinstalados } from 'app/shared/model/equiposinstalados.model';

@Component({
  selector: 'jhi-equiposinstalados-detail',
  templateUrl: './equiposinstalados-detail.component.html'
})
export class EquiposinstaladosDetailComponent implements OnInit {
  equiposinstalados: IEquiposinstalados | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equiposinstalados }) => (this.equiposinstalados = equiposinstalados));
  }

  previousState(): void {
    window.history.back();
  }
}
