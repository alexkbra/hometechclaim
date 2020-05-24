import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RequerimientoToSolucionService } from './requerimiento-to-solucion.service';
import { RequerimientoToSolucionDeleteDialogComponent } from './requerimiento-to-solucion-delete-dialog.component';

@Component({
  selector: 'jhi-requerimiento-to-solucion',
  templateUrl: './requerimiento-to-solucion.component.html'
})
export class RequerimientoToSolucionComponent implements OnInit, OnDestroy {
  requerimientoToSolucions?: IRequerimientoToSolucion[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected requerimientoToSolucionService: RequerimientoToSolucionService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.requerimientoToSolucionService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IRequerimientoToSolucion[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInRequerimientoToSolucions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRequerimientoToSolucion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRequerimientoToSolucions(): void {
    this.eventSubscriber = this.eventManager.subscribe('requerimientoToSolucionListModification', () => this.loadPage());
  }

  delete(requerimientoToSolucion: IRequerimientoToSolucion): void {
    const modalRef = this.modalService.open(RequerimientoToSolucionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.requerimientoToSolucion = requerimientoToSolucion;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IRequerimientoToSolucion[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/requerimiento-to-solucion'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.requerimientoToSolucions = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
