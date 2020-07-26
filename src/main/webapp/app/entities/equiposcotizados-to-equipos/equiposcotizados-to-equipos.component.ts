import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EquiposcotizadosToEquiposService } from './equiposcotizados-to-equipos.service';
import { EquiposcotizadosToEquiposDeleteDialogComponent } from './equiposcotizados-to-equipos-delete-dialog.component';

@Component({
  selector: 'jhi-equiposcotizados-to-equipos',
  templateUrl: './equiposcotizados-to-equipos.component.html'
})
export class EquiposcotizadosToEquiposComponent implements OnInit, OnDestroy {
  equiposcotizadosToEquipos?: IEquiposcotizadosToEquipos[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected equiposcotizadosToEquiposService: EquiposcotizadosToEquiposService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.equiposcotizadosToEquiposService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEquiposcotizadosToEquipos[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInEquiposcotizadosToEquipos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEquiposcotizadosToEquipos): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEquiposcotizadosToEquipos(): void {
    this.eventSubscriber = this.eventManager.subscribe('equiposcotizadosToEquiposListModification', () => this.loadPage());
  }

  delete(equiposcotizadosToEquipos: IEquiposcotizadosToEquipos): void {
    const modalRef = this.modalService.open(EquiposcotizadosToEquiposDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.equiposcotizadosToEquipos = equiposcotizadosToEquipos;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'asc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEquiposcotizadosToEquipos[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/equiposcotizados-to-equipos'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.equiposcotizadosToEquipos = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
