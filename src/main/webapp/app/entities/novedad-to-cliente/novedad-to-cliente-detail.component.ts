import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INovedadToCliente } from 'app/shared/model/novedad-to-cliente.model';

@Component({
  selector: 'jhi-novedad-to-cliente-detail',
  templateUrl: './novedad-to-cliente-detail.component.html'
})
export class NovedadToClienteDetailComponent implements OnInit {
  novedadToCliente: INovedadToCliente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ novedadToCliente }) => (this.novedadToCliente = novedadToCliente));
  }

  previousState(): void {
    window.history.back();
  }
}
