import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INotificaciones, Notificaciones } from 'app/shared/model/notificaciones.model';
import { NotificacionesService } from './notificaciones.service';
import { NotificacionesComponent } from './notificaciones.component';
import { NotificacionesDetailComponent } from './notificaciones-detail.component';
import { NotificacionesUpdateComponent } from './notificaciones-update.component';

@Injectable({ providedIn: 'root' })
export class NotificacionesResolve implements Resolve<INotificaciones> {
  constructor(private service: NotificacionesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotificaciones> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((notificaciones: HttpResponse<Notificaciones>) => {
          if (notificaciones.body) {
            return of(notificaciones.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Notificaciones());
  }
}

export const notificacionesRoute: Routes = [
  {
    path: '',
    component: NotificacionesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.notificaciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NotificacionesDetailComponent,
    resolve: {
      notificaciones: NotificacionesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.notificaciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NotificacionesUpdateComponent,
    resolve: {
      notificaciones: NotificacionesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.notificaciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NotificacionesUpdateComponent,
    resolve: {
      notificaciones: NotificacionesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.notificaciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
