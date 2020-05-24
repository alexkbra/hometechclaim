import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEquiposcotizadosToEquipos, EquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';
import { EquiposcotizadosToEquiposService } from './equiposcotizados-to-equipos.service';
import { EquiposcotizadosToEquiposComponent } from './equiposcotizados-to-equipos.component';
import { EquiposcotizadosToEquiposDetailComponent } from './equiposcotizados-to-equipos-detail.component';
import { EquiposcotizadosToEquiposUpdateComponent } from './equiposcotizados-to-equipos-update.component';

@Injectable({ providedIn: 'root' })
export class EquiposcotizadosToEquiposResolve implements Resolve<IEquiposcotizadosToEquipos> {
  constructor(private service: EquiposcotizadosToEquiposService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEquiposcotizadosToEquipos> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((equiposcotizadosToEquipos: HttpResponse<EquiposcotizadosToEquipos>) => {
          if (equiposcotizadosToEquipos.body) {
            return of(equiposcotizadosToEquipos.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EquiposcotizadosToEquipos());
  }
}

export const equiposcotizadosToEquiposRoute: Routes = [
  {
    path: '',
    component: EquiposcotizadosToEquiposComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'hometechclaimApp.equiposcotizadosToEquipos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EquiposcotizadosToEquiposDetailComponent,
    resolve: {
      equiposcotizadosToEquipos: EquiposcotizadosToEquiposResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.equiposcotizadosToEquipos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EquiposcotizadosToEquiposUpdateComponent,
    resolve: {
      equiposcotizadosToEquipos: EquiposcotizadosToEquiposResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.equiposcotizadosToEquipos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EquiposcotizadosToEquiposUpdateComponent,
    resolve: {
      equiposcotizadosToEquipos: EquiposcotizadosToEquiposResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hometechclaimApp.equiposcotizadosToEquipos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
