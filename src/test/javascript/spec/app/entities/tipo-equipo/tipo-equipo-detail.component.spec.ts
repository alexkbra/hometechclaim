import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HometechclaimTestModule } from '../../../test.module';
import { TipoEquipoDetailComponent } from 'app/entities/tipo-equipo/tipo-equipo-detail.component';
import { TipoEquipo } from 'app/shared/model/tipo-equipo.model';

describe('Component Tests', () => {
  describe('TipoEquipo Management Detail Component', () => {
    let comp: TipoEquipoDetailComponent;
    let fixture: ComponentFixture<TipoEquipoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ tipoEquipo: new TipoEquipo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [TipoEquipoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoEquipoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoEquipoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load tipoEquipo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoEquipo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
