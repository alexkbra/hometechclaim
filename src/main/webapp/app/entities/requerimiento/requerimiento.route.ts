import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRequerimiento, Requerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from './requerimiento.service';
import { RequerimientoComponent } from './requerimiento.component';
import { RequerimientoDetailComponent } from './requerimiento-detail.component';
import { RequerimientoUpdateComponent } from './requerimiento-update.component';

@Injectable({ providedIn: 'root' })
export class RequerimientoResolve implements Resolve<IRequerimiento> {
  constructor(private service: RequerimientoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRequerimiento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((requerimiento: HttpResponse<Requerimiento>) => {
          if (requerimiento.body) {
            return of(requerimiento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Requerimiento());
  }
}

export const requerimientoRoute: Routes = [
  {
    path: '',
    component: RequerimientoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.requerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RequerimientoDetailComponent,
    resolve: {
      requerimiento: RequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.requerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RequerimientoUpdateComponent,
    resolve: {
      requerimiento: RequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.requerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RequerimientoUpdateComponent,
    resolve: {
      requerimiento: RequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.requerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
