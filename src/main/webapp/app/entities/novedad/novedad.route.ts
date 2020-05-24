import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INovedad, Novedad } from 'app/shared/model/novedad.model';
import { NovedadService } from './novedad.service';
import { NovedadComponent } from './novedad.component';
import { NovedadDetailComponent } from './novedad-detail.component';
import { NovedadUpdateComponent } from './novedad-update.component';

@Injectable({ providedIn: 'root' })
export class NovedadResolve implements Resolve<INovedad> {
  constructor(private service: NovedadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INovedad> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((novedad: HttpResponse<Novedad>) => {
          if (novedad.body) {
            return of(novedad.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Novedad());
  }
}

export const novedadRoute: Routes = [
  {
    path: '',
    component: NovedadComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.novedad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NovedadDetailComponent,
    resolve: {
      novedad: NovedadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.novedad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NovedadUpdateComponent,
    resolve: {
      novedad: NovedadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.novedad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NovedadUpdateComponent,
    resolve: {
      novedad: NovedadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.novedad.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
