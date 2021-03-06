import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEquiposinstalados } from 'app/shared/model/equiposinstalados.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EquiposinstaladosService } from './equiposinstalados.service';
import { EquiposinstaladosDeleteDialogComponent } from './equiposinstalados-delete-dialog.component';

@Component({
  selector: 'jhi-equiposinstalados',
  templateUrl: './equiposinstalados.component.html'
})
export class EquiposinstaladosComponent implements OnInit, OnDestroy {
  equiposinstalados?: IEquiposinstalados[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected equiposinstaladosService: EquiposinstaladosService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.equiposinstaladosService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEquiposinstalados[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInEquiposinstalados();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEquiposinstalados): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEquiposinstalados(): void {
    this.eventSubscriber = this.eventManager.subscribe('equiposinstaladosListModification', () => this.loadPage());
  }

  delete(equiposinstalados: IEquiposinstalados): void {
    const modalRef = this.modalService.open(EquiposinstaladosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.equiposinstalados = equiposinstalados;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'asc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEquiposinstalados[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/equiposinstalados'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.equiposinstalados = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
