import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoSolucion, TipoSolucion } from 'app/shared/model/tipo-solucion.model';
import { TipoSolucionService } from './tipo-solucion.service';
import { TipoSolucionComponent } from './tipo-solucion.component';
import { TipoSolucionDetailComponent } from './tipo-solucion-detail.component';
import { TipoSolucionUpdateComponent } from './tipo-solucion-update.component';

@Injectable({ providedIn: 'root' })
export class TipoSolucionResolve implements Resolve<ITipoSolucion> {
  constructor(private service: TipoSolucionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoSolucion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoSolucion: HttpResponse<TipoSolucion>) => {
          if (tipoSolucion.body) {
            return of(tipoSolucion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoSolucion());
  }
}

export const tipoSolucionRoute: Routes = [
  {
    path: '',
    component: TipoSolucionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.tipoSolucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoSolucionDetailComponent,
    resolve: {
      tipoSolucion: TipoSolucionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.tipoSolucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoSolucionUpdateComponent,
    resolve: {
      tipoSolucion: TipoSolucionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.tipoSolucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoSolucionUpdateComponent,
    resolve: {
      tipoSolucion: TipoSolucionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.tipoSolucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
