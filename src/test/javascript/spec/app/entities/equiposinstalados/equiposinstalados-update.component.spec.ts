import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { EquiposinstaladosUpdateComponent } from 'app/entities/equiposinstalados/equiposinstalados-update.component';
import { EquiposinstaladosService } from 'app/entities/equiposinstalados/equiposinstalados.service';
import { Equiposinstalados } from 'app/shared/model/equiposinstalados.model';

describe('Component Tests', () => {
  describe('Equiposinstalados Management Update Component', () => {
    let comp: EquiposinstaladosUpdateComponent;
    let fixture: ComponentFixture<EquiposinstaladosUpdateComponent>;
    let service: EquiposinstaladosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [EquiposinstaladosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EquiposinstaladosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EquiposinstaladosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquiposinstaladosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Equiposinstalados(123);
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
        const entity = new Equiposinstalados();
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
