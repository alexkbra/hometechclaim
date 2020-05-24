import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { HometechclaimTestModule } from '../../../test.module';
import { RequerimientoToSolucionComponent } from 'app/entities/requerimiento-to-solucion/requerimiento-to-solucion.component';
import { RequerimientoToSolucionService } from 'app/entities/requerimiento-to-solucion/requerimiento-to-solucion.service';
import { RequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';

describe('Component Tests', () => {
  describe('RequerimientoToSolucion Management Component', () => {
    let comp: RequerimientoToSolucionComponent;
    let fixture: ComponentFixture<RequerimientoToSolucionComponent>;
    let service: RequerimientoToSolucionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [RequerimientoToSolucionComponent],
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
        .overrideTemplate(RequerimientoToSolucionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequerimientoToSolucionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequerimientoToSolucionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RequerimientoToSolucion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.requerimientoToSolucions && comp.requerimientoToSolucions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RequerimientoToSolucion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.requerimientoToSolucions && comp.requerimientoToSolucions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
