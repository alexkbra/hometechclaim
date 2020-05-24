import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIntereses } from 'app/shared/model/intereses.model';

@Component({
  selector: 'jhi-intereses-detail',
  templateUrl: './intereses-detail.component.html'
})
export class InteresesDetailComponent implements OnInit {
  intereses: IIntereses | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ intereses }) => (this.intereses = intereses));
  }

  previousState(): void {
    window.history.back();
  }
}
