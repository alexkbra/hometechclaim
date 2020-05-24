import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInteresesToCliente } from 'app/shared/model/intereses-to-cliente.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InteresesToClienteService } from './intereses-to-cliente.service';
import { InteresesToClienteDeleteDialogComponent } from './intereses-to-cliente-delete-dialog.component';

@Component({
  selector: 'jhi-intereses-to-cliente',
  templateUrl: './intereses-to-cliente.component.html'
})
export class InteresesToClienteComponent implements OnInit, OnDestroy {
  interesesToClientes?: IInteresesToCliente[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected interesesToClienteService: InteresesToClienteService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.interesesToClienteService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IInteresesToCliente[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInInteresesToClientes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInteresesToCliente): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInteresesToClientes(): void {
    this.eventSubscriber = this.eventManager.subscribe('interesesToClienteListModification', () => this.loadPage());
  }

  delete(interesesToCliente: IInteresesToCliente): void {
    const modalRef = this.modalService.open(InteresesToClienteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.interesesToCliente = interesesToCliente;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IInteresesToCliente[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/intereses-to-cliente'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.interesesToClientes = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
