import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { HometechclaimTestModule } from '../../../test.module';
import { NovedadToClienteComponent } from 'app/entities/novedad-to-cliente/novedad-to-cliente.component';
import { NovedadToClienteService } from 'app/entities/novedad-to-cliente/novedad-to-cliente.service';
import { NovedadToCliente } from 'app/shared/model/novedad-to-cliente.model';

describe('Component Tests', () => {
  describe('NovedadToCliente Management Component', () => {
    let comp: NovedadToClienteComponent;
    let fixture: ComponentFixture<NovedadToClienteComponent>;
    let service: NovedadToClienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [NovedadToClienteComponent],
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
        .overrideTemplate(NovedadToClienteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NovedadToClienteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NovedadToClienteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NovedadToCliente(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.novedadToClientes && comp.novedadToClientes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NovedadToCliente(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.novedadToClientes && comp.novedadToClientes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
