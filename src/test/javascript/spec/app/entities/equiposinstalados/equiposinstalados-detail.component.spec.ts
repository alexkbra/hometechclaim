import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { EquiposinstaladosDetailComponent } from 'app/entities/equiposinstalados/equiposinstalados-detail.component';
import { Equiposinstalados } from 'app/shared/model/equiposinstalados.model';

describe('Component Tests', () => {
  describe('Equiposinstalados Management Detail Component', () => {
    let comp: EquiposinstaladosDetailComponent;
    let fixture: ComponentFixture<EquiposinstaladosDetailComponent>;
    const route = ({ data: of({ equiposinstalados: new Equiposinstalados(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [EquiposinstaladosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EquiposinstaladosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EquiposinstaladosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load equiposinstalados on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.equiposinstalados).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
