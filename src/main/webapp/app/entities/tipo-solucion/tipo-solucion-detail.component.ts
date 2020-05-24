import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITipoSolucion } from 'app/shared/model/tipo-solucion.model';

@Component({
  selector: 'jhi-tipo-solucion-detail',
  templateUrl: './tipo-solucion-detail.component.html'
})
export class TipoSolucionDetailComponent implements OnInit {
  tipoSolucion: ITipoSolucion | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoSolucion }) => (this.tipoSolucion = tipoSolucion));
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
