import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICotizacion } from 'app/shared/model/cotizacion.model';

@Component({
  selector: 'jhi-cotizacion-detail',
  templateUrl: './cotizacion-detail.component.html'
})
export class CotizacionDetailComponent implements OnInit {
  cotizacion: ICotizacion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cotizacion }) => (this.cotizacion = cotizacion));
  }

  previousState(): void {
    window.history.back();
  }
}
