import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { RequerimientoToSolucionUpdateComponent } from 'app/entities/requerimiento-to-solucion/requerimiento-to-solucion-update.component';
import { RequerimientoToSolucionService } from 'app/entities/requerimiento-to-solucion/requerimiento-to-solucion.service';
import { RequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';

describe('Component Tests', () => {
  describe('RequerimientoToSolucion Management Update Component', () => {
    let comp: RequerimientoToSolucionUpdateComponent;
    let fixture: ComponentFixture<RequerimientoToSolucionUpdateComponent>;
    let service: RequerimientoToSolucionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [RequerimientoToSolucionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RequerimientoToSolucionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequerimientoToSolucionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequerimientoToSolucionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RequerimientoToSolucion(123);
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
        const entity = new RequerimientoToSolucion();
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
