import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { TipoSolucionUpdateComponent } from 'app/entities/tipo-solucion/tipo-solucion-update.component';
import { TipoSolucionService } from 'app/entities/tipo-solucion/tipo-solucion.service';
import { TipoSolucion } from 'app/shared/model/tipo-solucion.model';

describe('Component Tests', () => {
  describe('TipoSolucion Management Update Component', () => {
    let comp: TipoSolucionUpdateComponent;
    let fixture: ComponentFixture<TipoSolucionUpdateComponent>;
    let service: TipoSolucionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [TipoSolucionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoSolucionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoSolucionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoSolucionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoSolucion(123);
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
        const entity = new TipoSolucion();
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
