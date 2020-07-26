import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICotizacion } from 'app/shared/model/cotizacion.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CotizacionService } from './cotizacion.service';
import { CotizacionDeleteDialogComponent } from './cotizacion-delete-dialog.component';

@Component({
  selector: 'jhi-cotizacion',
  templateUrl: './cotizacion.component.html'
})
export class CotizacionComponent implements OnInit, OnDestroy {
  cotizacions?: ICotizacion[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected cotizacionService: CotizacionService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.cotizacionService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICotizacion[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInCotizacions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICotizacion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCotizacions(): void {
    this.eventSubscriber = this.eventManager.subscribe('cotizacionListModification', () => this.loadPage());
  }

  delete(cotizacion: ICotizacion): void {
    const modalRef = this.modalService.open(CotizacionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cotizacion = cotizacion;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'asc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICotizacion[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/cotizacion'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.cotizacions = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
