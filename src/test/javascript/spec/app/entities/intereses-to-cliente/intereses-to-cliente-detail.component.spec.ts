import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { InteresesToClienteDetailComponent } from 'app/entities/intereses-to-cliente/intereses-to-cliente-detail.component';
import { InteresesToCliente } from 'app/shared/model/intereses-to-cliente.model';

describe('Component Tests', () => {
  describe('InteresesToCliente Management Detail Component', () => {
    let comp: InteresesToClienteDetailComponent;
    let fixture: ComponentFixture<InteresesToClienteDetailComponent>;
    const route = ({ data: of({ interesesToCliente: new InteresesToCliente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [InteresesToClienteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InteresesToClienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InteresesToClienteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load interesesToCliente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.interesesToCliente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
