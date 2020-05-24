import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISolucion, Solucion } from 'app/shared/model/solucion.model';
import { SolucionService } from './solucion.service';
import { SolucionComponent } from './solucion.component';
import { SolucionDetailComponent } from './solucion-detail.component';
import { SolucionUpdateComponent } from './solucion-update.component';

@Injectable({ providedIn: 'root' })
export class SolucionResolve implements Resolve<ISolucion> {
  constructor(private service: SolucionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISolucion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((solucion: HttpResponse<Solucion>) => {
          if (solucion.body) {
            return of(solucion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Solucion());
  }
}

export const solucionRoute: Routes = [
  {
    path: '',
    component: SolucionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.solucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SolucionDetailComponent,
    resolve: {
      solucion: SolucionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.solucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SolucionUpdateComponent,
    resolve: {
      solucion: SolucionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.solucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SolucionUpdateComponent,
    resolve: {
      solucion: SolucionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.solucion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
