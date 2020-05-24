import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { RequerimientoToSolucionDetailComponent } from 'app/entities/requerimiento-to-solucion/requerimiento-to-solucion-detail.component';
import { RequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';

describe('Component Tests', () => {
  describe('RequerimientoToSolucion Management Detail Component', () => {
    let comp: RequerimientoToSolucionDetailComponent;
    let fixture: ComponentFixture<RequerimientoToSolucionDetailComponent>;
    const route = ({ data: of({ requerimientoToSolucion: new RequerimientoToSolucion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [RequerimientoToSolucionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RequerimientoToSolucionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RequerimientoToSolucionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load requerimientoToSolucion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.requerimientoToSolucion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
