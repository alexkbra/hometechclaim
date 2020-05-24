import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INovedadToCliente } from 'app/shared/model/novedad-to-cliente.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { NovedadToClienteService } from './novedad-to-cliente.service';
import { NovedadToClienteDeleteDialogComponent } from './novedad-to-cliente-delete-dialog.component';

@Component({
  selector: 'jhi-novedad-to-cliente',
  templateUrl: './novedad-to-cliente.component.html'
})
export class NovedadToClienteComponent implements OnInit, OnDestroy {
  novedadToClientes?: INovedadToCliente[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected novedadToClienteService: NovedadToClienteService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.novedadToClienteService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<INovedadToCliente[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInNovedadToClientes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INovedadToCliente): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNovedadToClientes(): void {
    this.eventSubscriber = this.eventManager.subscribe('novedadToClienteListModification', () => this.loadPage());
  }

  delete(novedadToCliente: INovedadToCliente): void {
    const modalRef = this.modalService.open(NovedadToClienteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.novedadToCliente = novedadToCliente;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: INovedadToCliente[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/novedad-to-cliente'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.novedadToClientes = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
