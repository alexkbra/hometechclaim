import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIntereses, Intereses } from 'app/shared/model/intereses.model';
import { InteresesService } from './intereses.service';
import { InteresesComponent } from './intereses.component';
import { InteresesDetailComponent } from './intereses-detail.component';
import { InteresesUpdateComponent } from './intereses-update.component';

@Injectable({ providedIn: 'root' })
export class InteresesResolve implements Resolve<IIntereses> {
  constructor(private service: InteresesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIntereses> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((intereses: HttpResponse<Intereses>) => {
          if (intereses.body) {
            return of(intereses.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Intereses());
  }
}

export const interesesRoute: Routes = [
  {
    path: '',
    component: InteresesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.intereses.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InteresesDetailComponent,
    resolve: {
      intereses: InteresesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.intereses.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InteresesUpdateComponent,
    resolve: {
      intereses: InteresesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.intereses.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InteresesUpdateComponent,
    resolve: {
      intereses: InteresesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.intereses.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
