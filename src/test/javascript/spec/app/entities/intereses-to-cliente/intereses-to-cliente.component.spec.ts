import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { HometechclaimTestModule } from '../../../test.module';
import { InteresesToClienteComponent } from 'app/entities/intereses-to-cliente/intereses-to-cliente.component';
import { InteresesToClienteService } from 'app/entities/intereses-to-cliente/intereses-to-cliente.service';
import { InteresesToCliente } from 'app/shared/model/intereses-to-cliente.model';

describe('Component Tests', () => {
  describe('InteresesToCliente Management Component', () => {
    let comp: InteresesToClienteComponent;
    let fixture: ComponentFixture<InteresesToClienteComponent>;
    let service: InteresesToClienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [InteresesToClienteComponent],
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
        .overrideTemplate(InteresesToClienteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InteresesToClienteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InteresesToClienteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InteresesToCliente(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.interesesToClientes && comp.interesesToClientes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InteresesToCliente(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.interesesToClientes && comp.interesesToClientes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
