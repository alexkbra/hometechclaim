import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IColaboradores } from 'app/shared/model/colaboradores.model';

@Component({
  selector: 'jhi-colaboradores-detail',
  templateUrl: './colaboradores-detail.component.html'
})
export class ColaboradoresDetailComponent implements OnInit {
  colaboradores: IColaboradores | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ colaboradores }) => (this.colaboradores = colaboradores));
  }

  previousState(): void {
    window.history.back();
  }
}
