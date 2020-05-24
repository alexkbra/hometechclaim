import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { InteresesToClienteUpdateComponent } from 'app/entities/intereses-to-cliente/intereses-to-cliente-update.component';
import { InteresesToClienteService } from 'app/entities/intereses-to-cliente/intereses-to-cliente.service';
import { InteresesToCliente } from 'app/shared/model/intereses-to-cliente.model';

describe('Component Tests', () => {
  describe('InteresesToCliente Management Update Component', () => {
    let comp: InteresesToClienteUpdateComponent;
    let fixture: ComponentFixture<InteresesToClienteUpdateComponent>;
    let service: InteresesToClienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [InteresesToClienteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InteresesToClienteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InteresesToClienteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InteresesToClienteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InteresesToCliente(123);
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
        const entity = new InteresesToCliente();
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
