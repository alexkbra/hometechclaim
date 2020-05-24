import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { NovedadToClienteUpdateComponent } from 'app/entities/novedad-to-cliente/novedad-to-cliente-update.component';
import { NovedadToClienteService } from 'app/entities/novedad-to-cliente/novedad-to-cliente.service';
import { NovedadToCliente } from 'app/shared/model/novedad-to-cliente.model';

describe('Component Tests', () => {
  describe('NovedadToCliente Management Update Component', () => {
    let comp: NovedadToClienteUpdateComponent;
    let fixture: ComponentFixture<NovedadToClienteUpdateComponent>;
    let service: NovedadToClienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [NovedadToClienteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NovedadToClienteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NovedadToClienteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NovedadToClienteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NovedadToCliente(123);
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
        const entity = new NovedadToCliente();
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
