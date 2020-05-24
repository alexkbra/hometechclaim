import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IColaboradores, Colaboradores } from 'app/shared/model/colaboradores.model';
import { ColaboradoresService } from './colaboradores.service';
import { ColaboradoresComponent } from './colaboradores.component';
import { ColaboradoresDetailComponent } from './colaboradores-detail.component';
import { ColaboradoresUpdateComponent } from './colaboradores-update.component';

@Injectable({ providedIn: 'root' })
export class ColaboradoresResolve implements Resolve<IColaboradores> {
  constructor(private service: ColaboradoresService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IColaboradores> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((colaboradores: HttpResponse<Colaboradores>) => {
          if (colaboradores.body) {
            return of(colaboradores.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Colaboradores());
  }
}

export const colaboradoresRoute: Routes = [
  {
    path: '',
    component: ColaboradoresComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.colaboradores.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ColaboradoresDetailComponent,
    resolve: {
      colaboradores: ColaboradoresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.colaboradores.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ColaboradoresUpdateComponent,
    resolve: {
      colaboradores: ColaboradoresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.colaboradores.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ColaboradoresUpdateComponent,
    resolve: {
      colaboradores: ColaboradoresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.colaboradores.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
