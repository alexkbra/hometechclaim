import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { ComercialesUpdateComponent } from 'app/entities/comerciales/comerciales-update.component';
import { ComercialesService } from 'app/entities/comerciales/comerciales.service';
import { Comerciales } from 'app/shared/model/comerciales.model';

describe('Component Tests', () => {
  describe('Comerciales Management Update Component', () => {
    let comp: ComercialesUpdateComponent;
    let fixture: ComponentFixture<ComercialesUpdateComponent>;
    let service: ComercialesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [ComercialesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ComercialesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComercialesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComercialesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Comerciales(123);
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
        const entity = new Comerciales();
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
