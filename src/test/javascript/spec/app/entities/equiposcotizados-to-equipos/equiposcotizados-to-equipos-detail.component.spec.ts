import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { EquiposcotizadosToEquiposDetailComponent } from 'app/entities/equiposcotizados-to-equipos/equiposcotizados-to-equipos-detail.component';
import { EquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';

describe('Component Tests', () => {
  describe('EquiposcotizadosToEquipos Management Detail Component', () => {
    let comp: EquiposcotizadosToEquiposDetailComponent;
    let fixture: ComponentFixture<EquiposcotizadosToEquiposDetailComponent>;
    const route = ({ data: of({ equiposcotizadosToEquipos: new EquiposcotizadosToEquipos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [EquiposcotizadosToEquiposDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EquiposcotizadosToEquiposDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EquiposcotizadosToEquiposDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load equiposcotizadosToEquipos on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.equiposcotizadosToEquipos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
