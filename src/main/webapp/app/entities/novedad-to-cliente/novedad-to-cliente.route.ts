import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INovedadToCliente, NovedadToCliente } from 'app/shared/model/novedad-to-cliente.model';
import { NovedadToClienteService } from './novedad-to-cliente.service';
import { NovedadToClienteComponent } from './novedad-to-cliente.component';
import { NovedadToClienteDetailComponent } from './novedad-to-cliente-detail.component';
import { NovedadToClienteUpdateComponent } from './novedad-to-cliente-update.component';

@Injectable({ providedIn: 'root' })
export class NovedadToClienteResolve implements Resolve<INovedadToCliente> {
  constructor(private service: NovedadToClienteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INovedadToCliente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((novedadToCliente: HttpResponse<NovedadToCliente>) => {
          if (novedadToCliente.body) {
            return of(novedadToCliente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NovedadToCliente());
  }
}

export const novedadToClienteRoute: Routes = [
  {
    path: '',
    component: NovedadToClienteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.novedadToCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NovedadToClienteDetailComponent,
    resolve: {
      novedadToCliente: NovedadToClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.novedadToCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NovedadToClienteUpdateComponent,
    resolve: {
      novedadToCliente: NovedadToClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.novedadToCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NovedadToClienteUpdateComponent,
    resolve: {
      novedadToCliente: NovedadToClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.novedadToCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
