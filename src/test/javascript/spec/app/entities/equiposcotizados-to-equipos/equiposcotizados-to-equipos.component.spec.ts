import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { HometechclaimTestModule } from '../../../test.module';
import { EquiposcotizadosToEquiposComponent } from 'app/entities/equiposcotizados-to-equipos/equiposcotizados-to-equipos.component';
import { EquiposcotizadosToEquiposService } from 'app/entities/equiposcotizados-to-equipos/equiposcotizados-to-equipos.service';
import { EquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';

describe('Component Tests', () => {
  describe('EquiposcotizadosToEquipos Management Component', () => {
    let comp: EquiposcotizadosToEquiposComponent;
    let fixture: ComponentFixture<EquiposcotizadosToEquiposComponent>;
    let service: EquiposcotizadosToEquiposService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [EquiposcotizadosToEquiposComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(EquiposcotizadosToEquiposComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EquiposcotizadosToEquiposComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquiposcotizadosToEquiposService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EquiposcotizadosToEquipos(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.equiposcotizadosToEquipos && comp.equiposcotizadosToEquipos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EquiposcotizadosToEquipos(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.equiposcotizadosToEquipos && comp.equiposcotizadosToEquipos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
