import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { NovedadUpdateComponent } from 'app/entities/novedad/novedad-update.component';
import { NovedadService } from 'app/entities/novedad/novedad.service';
import { Novedad } from 'app/shared/model/novedad.model';

describe('Component Tests', () => {
  describe('Novedad Management Update Component', () => {
    let comp: NovedadUpdateComponent;
    let fixture: ComponentFixture<NovedadUpdateComponent>;
    let service: NovedadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [NovedadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NovedadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NovedadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NovedadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Novedad(123);
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
        const entity = new Novedad();
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
