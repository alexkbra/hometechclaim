import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { NotificacionesUpdateComponent } from 'app/entities/notificaciones/notificaciones-update.component';
import { NotificacionesService } from 'app/entities/notificaciones/notificaciones.service';
import { Notificaciones } from 'app/shared/model/notificaciones.model';

describe('Component Tests', () => {
  describe('Notificaciones Management Update Component', () => {
    let comp: NotificacionesUpdateComponent;
    let fixture: ComponentFixture<NotificacionesUpdateComponent>;
    let service: NotificacionesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [NotificacionesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NotificacionesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotificacionesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotificacionesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Notificaciones(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Notificaciones();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
