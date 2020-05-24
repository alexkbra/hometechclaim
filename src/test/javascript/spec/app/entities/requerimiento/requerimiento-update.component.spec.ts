import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { RequerimientoUpdateComponent } from 'app/entities/requerimiento/requerimiento-update.component';
import { RequerimientoService } from 'app/entities/requerimiento/requerimiento.service';
import { Requerimiento } from 'app/shared/model/requerimiento.model';

describe('Component Tests', () => {
  describe('Requerimiento Management Update Component', () => {
    let comp: RequerimientoUpdateComponent;
    let fixture: ComponentFixture<RequerimientoUpdateComponent>;
    let service: RequerimientoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [RequerimientoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RequerimientoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequerimientoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequerimientoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Requerimiento(123);
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
        const entity = new Requerimiento();
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
