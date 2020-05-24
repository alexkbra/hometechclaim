import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { ColaboradoresUpdateComponent } from 'app/entities/colaboradores/colaboradores-update.component';
import { ColaboradoresService } from 'app/entities/colaboradores/colaboradores.service';
import { Colaboradores } from 'app/shared/model/colaboradores.model';

describe('Component Tests', () => {
  describe('Colaboradores Management Update Component', () => {
    let comp: ColaboradoresUpdateComponent;
    let fixture: ComponentFixture<ColaboradoresUpdateComponent>;
    let service: ColaboradoresService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [ColaboradoresUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ColaboradoresUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ColaboradoresUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ColaboradoresService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Colaboradores(123);
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
        const entity = new Colaboradores();
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
