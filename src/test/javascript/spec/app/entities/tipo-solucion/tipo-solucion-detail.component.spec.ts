import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HometechclaimTestModule } from '../../../test.module';
import { TipoSolucionDetailComponent } from 'app/entities/tipo-solucion/tipo-solucion-detail.component';
import { TipoSolucion } from 'app/shared/model/tipo-solucion.model';

describe('Component Tests', () => {
  describe('TipoSolucion Management Detail Component', () => {
    let comp: TipoSolucionDetailComponent;
    let fixture: ComponentFixture<TipoSolucionDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ tipoSolucion: new TipoSolucion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [TipoSolucionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoSolucionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoSolucionDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load tipoSolucion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoSolucion).toEqual(jasmine.objectContaining({ id: 123 }));
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
