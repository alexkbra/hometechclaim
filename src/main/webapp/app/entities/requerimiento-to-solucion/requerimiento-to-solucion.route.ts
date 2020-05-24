import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRequerimientoToSolucion, RequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';
import { RequerimientoToSolucionService } from './requerimiento-to-solucion.service';
import { RequerimientoToSolucionComponent } from './requerimiento-to-solucion.component';
import { RequerimientoToSolucionDetailComponent } from './requerimiento-to-solucion-detail.component';
import { RequerimientoToSolucionUpdateComponent } from './requerimiento-to-solucion-update.component';

@Injectable({ providedIn: 'root' })
export class RequerimientoToSolucionResolve implements Resolve<IRequerimientoToSolucion> {
  constructor(private service: RequerimientoToSolucionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRequerimientoToSolucion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((requerimientoToSolucion: HttpResponse<RequerimientoToSolucion>) => {
          if (requerimientoToSolucion.body) {
            return of(requerimientoToSolucion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RequerimientoToSolucion());
  }
}

export const requerimientoToSolucionRoute: Routes = [
  {
    path: '',
    component: RequerimientoToSolucionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.requerimientoToSolucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RequerimientoToSolucionDetailComponent,
    resolve: {
      requerimientoToSolucion: RequerimientoToSolucionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.requerimientoToSolucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RequerimientoToSolucionUpdateComponent,
    resolve: {
      requerimientoToSolucion: RequerimientoToSolucionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.requerimientoToSolucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RequerimientoToSolucionUpdateComponent,
    resolve: {
      requerimientoToSolucion: RequerimientoToSolucionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.requerimientoToSolucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
