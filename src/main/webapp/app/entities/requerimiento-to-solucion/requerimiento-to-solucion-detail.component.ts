import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';

@Component({
  selector: 'jhi-requerimiento-to-solucion-detail',
  templateUrl: './requerimiento-to-solucion-detail.component.html'
})
export class RequerimientoToSolucionDetailComponent implements OnInit {
  requerimientoToSolucion: IRequerimientoToSolucion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requerimientoToSolucion }) => (this.requerimientoToSolucion = requerimientoToSolucion));
  }

  previousState(): void {
    window.history.back();
  }
}
