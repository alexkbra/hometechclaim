import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { EquiposcotizadosToEquiposUpdateComponent } from 'app/entities/equiposcotizados-to-equipos/equiposcotizados-to-equipos-update.component';
import { EquiposcotizadosToEquiposService } from 'app/entities/equiposcotizados-to-equipos/equiposcotizados-to-equipos.service';
import { EquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';

describe('Component Tests', () => {
  describe('EquiposcotizadosToEquipos Management Update Component', () => {
    let comp: EquiposcotizadosToEquiposUpdateComponent;
    let fixture: ComponentFixture<EquiposcotizadosToEquiposUpdateComponent>;
    let service: EquiposcotizadosToEquiposService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [EquiposcotizadosToEquiposUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EquiposcotizadosToEquiposUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EquiposcotizadosToEquiposUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquiposcotizadosToEquiposService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EquiposcotizadosToEquipos(123);
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
        const entity = new EquiposcotizadosToEquipos();
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
