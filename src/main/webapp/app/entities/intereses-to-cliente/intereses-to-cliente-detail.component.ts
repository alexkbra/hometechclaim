import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInteresesToCliente } from 'app/shared/model/intereses-to-cliente.model';

@Component({
  selector: 'jhi-intereses-to-cliente-detail',
  templateUrl: './intereses-to-cliente-detail.component.html'
})
export class InteresesToClienteDetailComponent implements OnInit {
  interesesToCliente: IInteresesToCliente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interesesToCliente }) => (this.interesesToCliente = interesesToCliente));
  }

  previousState(): void {
    window.history.back();
  }
}
