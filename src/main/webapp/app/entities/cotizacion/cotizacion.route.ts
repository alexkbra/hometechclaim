import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICotizacion, Cotizacion } from 'app/shared/model/cotizacion.model';
import { CotizacionService } from './cotizacion.service';
import { CotizacionComponent } from './cotizacion.component';
import { CotizacionDetailComponent } from './cotizacion-detail.component';
import { CotizacionUpdateComponent } from './cotizacion-update.component';

@Injectable({ providedIn: 'root' })
export class CotizacionResolve implements Resolve<ICotizacion> {
  constructor(private service: CotizacionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICotizacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cotizacion: HttpResponse<Cotizacion>) => {
          if (cotizacion.body) {
            return of(cotizacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cotizacion());
  }
}

export const cotizacionRoute: Routes = [
  {
    path: '',
    component: CotizacionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.cotizacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CotizacionDetailComponent,
    resolve: {
      cotizacion: CotizacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.cotizacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CotizacionUpdateComponent,
    resolve: {
      cotizacion: CotizacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.cotizacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CotizacionUpdateComponent,
    resolve: {
      cotizacion: CotizacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.cotizacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
