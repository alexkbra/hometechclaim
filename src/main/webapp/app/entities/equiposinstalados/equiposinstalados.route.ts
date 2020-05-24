import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEquiposinstalados, Equiposinstalados } from 'app/shared/model/equiposinstalados.model';
import { EquiposinstaladosService } from './equiposinstalados.service';
import { EquiposinstaladosComponent } from './equiposinstalados.component';
import { EquiposinstaladosDetailComponent } from './equiposinstalados-detail.component';
import { EquiposinstaladosUpdateComponent } from './equiposinstalados-update.component';

@Injectable({ providedIn: 'root' })
export class EquiposinstaladosResolve implements Resolve<IEquiposinstalados> {
  constructor(private service: EquiposinstaladosService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEquiposinstalados> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((equiposinstalados: HttpResponse<Equiposinstalados>) => {
          if (equiposinstalados.body) {
            return of(equiposinstalados.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Equiposinstalados());
  }
}

export const equiposinstaladosRoute: Routes = [
  {
    path: '',
    component: EquiposinstaladosComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.equiposinstalados.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EquiposinstaladosDetailComponent,
    resolve: {
      equiposinstalados: EquiposinstaladosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.equiposinstalados.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EquiposinstaladosUpdateComponent,
    resolve: {
      equiposinstalados: EquiposinstaladosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.equiposinstalados.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EquiposinstaladosUpdateComponent,
    resolve: {
      equiposinstalados: EquiposinstaladosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.equiposinstalados.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
