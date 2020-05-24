import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IComerciales, Comerciales } from 'app/shared/model/comerciales.model';
import { ComercialesService } from './comerciales.service';
import { ComercialesComponent } from './comerciales.component';
import { ComercialesDetailComponent } from './comerciales-detail.component';
import { ComercialesUpdateComponent } from './comerciales-update.component';

@Injectable({ providedIn: 'root' })
export class ComercialesResolve implements Resolve<IComerciales> {
  constructor(private service: ComercialesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComerciales> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((comerciales: HttpResponse<Comerciales>) => {
          if (comerciales.body) {
            return of(comerciales.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Comerciales());
  }
}

export const comercialesRoute: Routes = [
  {
    path: '',
    component: ComercialesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.comerciales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComercialesDetailComponent,
    resolve: {
      comerciales: ComercialesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.comerciales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComercialesUpdateComponent,
    resolve: {
      comerciales: ComercialesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.comerciales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComercialesUpdateComponent,
    resolve: {
      comerciales: ComercialesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.comerciales.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
