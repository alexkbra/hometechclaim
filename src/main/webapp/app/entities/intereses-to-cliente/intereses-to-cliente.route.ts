import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInteresesToCliente, InteresesToCliente } from 'app/shared/model/intereses-to-cliente.model';
import { InteresesToClienteService } from './intereses-to-cliente.service';
import { InteresesToClienteComponent } from './intereses-to-cliente.component';
import { InteresesToClienteDetailComponent } from './intereses-to-cliente-detail.component';
import { InteresesToClienteUpdateComponent } from './intereses-to-cliente-update.component';

@Injectable({ providedIn: 'root' })
export class InteresesToClienteResolve implements Resolve<IInteresesToCliente> {
  constructor(private service: InteresesToClienteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInteresesToCliente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((interesesToCliente: HttpResponse<InteresesToCliente>) => {
          if (interesesToCliente.body) {
            return of(interesesToCliente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InteresesToCliente());
  }
}

export const interesesToClienteRoute: Routes = [
  {
    path: '',
    component: InteresesToClienteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.interesesToCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InteresesToClienteDetailComponent,
    resolve: {
      interesesToCliente: InteresesToClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.interesesToCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InteresesToClienteUpdateComponent,
    resolve: {
      interesesToCliente: InteresesToClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.interesesToCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InteresesToClienteUpdateComponent,
    resolve: {
      interesesToCliente: InteresesToClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.interesesToCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
