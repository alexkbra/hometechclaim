import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { INovedad } from 'app/shared/model/novedad.model';

@Component({
  selector: 'jhi-novedad-detail',
  templateUrl: './novedad-detail.component.html'
})
export class NovedadDetailComponent implements OnInit {
  novedad: INovedad | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ novedad }) => (this.novedad = novedad));
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
