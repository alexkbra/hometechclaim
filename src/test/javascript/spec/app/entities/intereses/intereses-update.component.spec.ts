import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { InteresesUpdateComponent } from 'app/entities/intereses/intereses-update.component';
import { InteresesService } from 'app/entities/intereses/intereses.service';
import { Intereses } from 'app/shared/model/intereses.model';

describe('Component Tests', () => {
  describe('Intereses Management Update Component', () => {
    let comp: InteresesUpdateComponent;
    let fixture: ComponentFixture<InteresesUpdateComponent>;
    let service: InteresesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [InteresesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InteresesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InteresesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InteresesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Intereses(123);
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
        const entity = new Intereses();
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
