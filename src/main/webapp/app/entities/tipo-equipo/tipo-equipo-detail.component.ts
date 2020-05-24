import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITipoEquipo } from 'app/shared/model/tipo-equipo.model';

@Component({
  selector: 'jhi-tipo-equipo-detail',
  templateUrl: './tipo-equipo-detail.component.html'
})
export class TipoEquipoDetailComponent implements OnInit {
  tipoEquipo: ITipoEquipo | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoEquipo }) => (this.tipoEquipo = tipoEquipo));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
