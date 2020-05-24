import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { SolucionUpdateComponent } from 'app/entities/solucion/solucion-update.component';
import { SolucionService } from 'app/entities/solucion/solucion.service';
import { Solucion } from 'app/shared/model/solucion.model';

describe('Component Tests', () => {
  describe('Solucion Management Update Component', () => {
    let comp: SolucionUpdateComponent;
    let fixture: ComponentFixture<SolucionUpdateComponent>;
    let service: SolucionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [SolucionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SolucionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SolucionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SolucionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Solucion(123);
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
        const entity = new Solucion();
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
