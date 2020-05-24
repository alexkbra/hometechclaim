import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { ColaboradoresDetailComponent } from 'app/entities/colaboradores/colaboradores-detail.component';
import { Colaboradores } from 'app/shared/model/colaboradores.model';

describe('Component Tests', () => {
  describe('Colaboradores Management Detail Component', () => {
    let comp: ColaboradoresDetailComponent;
    let fixture: ComponentFixture<ColaboradoresDetailComponent>;
    const route = ({ data: of({ colaboradores: new Colaboradores(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [ColaboradoresDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ColaboradoresDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ColaboradoresDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load colaboradores on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.colaboradores).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
