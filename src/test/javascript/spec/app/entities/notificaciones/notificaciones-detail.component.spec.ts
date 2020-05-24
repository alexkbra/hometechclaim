import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { NotificacionesDetailComponent } from 'app/entities/notificaciones/notificaciones-detail.component';
import { Notificaciones } from 'app/shared/model/notificaciones.model';

describe('Component Tests', () => {
  describe('Notificaciones Management Detail Component', () => {
    let comp: NotificacionesDetailComponent;
    let fixture: ComponentFixture<NotificacionesDetailComponent>;
    const route = ({ data: of({ notificaciones: new Notificaciones(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [NotificacionesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NotificacionesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotificacionesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load notificaciones on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.notificaciones).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
