import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { NovedadToClienteDetailComponent } from 'app/entities/novedad-to-cliente/novedad-to-cliente-detail.component';
import { NovedadToCliente } from 'app/shared/model/novedad-to-cliente.model';

describe('Component Tests', () => {
  describe('NovedadToCliente Management Detail Component', () => {
    let comp: NovedadToClienteDetailComponent;
    let fixture: ComponentFixture<NovedadToClienteDetailComponent>;
    const route = ({ data: of({ novedadToCliente: new NovedadToCliente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [NovedadToClienteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NovedadToClienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NovedadToClienteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load novedadToCliente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.novedadToCliente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
