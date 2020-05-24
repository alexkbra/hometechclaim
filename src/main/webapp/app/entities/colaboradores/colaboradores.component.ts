import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IColaboradores } from 'app/shared/model/colaboradores.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ColaboradoresService } from './colaboradores.service';
import { ColaboradoresDeleteDialogComponent } from './colaboradores-delete-dialog.component';

@Component({
  selector: 'jhi-colaboradores',
  templateUrl: './colaboradores.component.html'
})
export class ColaboradoresComponent implements OnInit, OnDestroy {
  colaboradores?: IColaboradores[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected colaboradoresService: ColaboradoresService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.colaboradoresService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IColaboradores[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInColaboradores();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IColaboradores): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInColaboradores(): void {
    this.eventSubscriber = this.eventManager.subscribe('colaboradoresListModification', () => this.loadPage());
  }

  delete(colaboradores: IColaboradores): void {
    const modalRef = this.modalService.open(ColaboradoresDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.colaboradores = colaboradores;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IColaboradores[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/colaboradores'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.colaboradores = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
