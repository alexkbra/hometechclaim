import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { CotizacionUpdateComponent } from 'app/entities/cotizacion/cotizacion-update.component';
import { CotizacionService } from 'app/entities/cotizacion/cotizacion.service';
import { Cotizacion } from 'app/shared/model/cotizacion.model';

describe('Component Tests', () => {
  describe('Cotizacion Management Update Component', () => {
    let comp: CotizacionUpdateComponent;
    let fixture: ComponentFixture<CotizacionUpdateComponent>;
    let service: CotizacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [CotizacionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CotizacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CotizacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CotizacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cotizacion(123);
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
        const entity = new Cotizacion();
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
