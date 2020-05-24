import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoEquipo, TipoEquipo } from 'app/shared/model/tipo-equipo.model';
import { TipoEquipoService } from './tipo-equipo.service';
import { TipoEquipoComponent } from './tipo-equipo.component';
import { TipoEquipoDetailComponent } from './tipo-equipo-detail.component';
import { TipoEquipoUpdateComponent } from './tipo-equipo-update.component';

@Injectable({ providedIn: 'root' })
export class TipoEquipoResolve implements Resolve<ITipoEquipo> {
  constructor(private service: TipoEquipoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoEquipo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoEquipo: HttpResponse<TipoEquipo>) => {
          if (tipoEquipo.body) {
            return of(tipoEquipo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoEquipo());
  }
}

export const tipoEquipoRoute: Routes = [
  {
    path: '',
    component: TipoEquipoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.tipoEquipo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoEquipoDetailComponent,
    resolve: {
      tipoEquipo: TipoEquipoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.tipoEquipo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoEquipoUpdateComponent,
    resolve: {
      tipoEquipo: TipoEquipoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.tipoEquipo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoEquipoUpdateComponent,
    resolve: {
      tipoEquipo: TipoEquipoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.tipoEquipo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
