import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HometechclaimTestModule } from '../../../test.module';
import { SolucionDetailComponent } from 'app/entities/solucion/solucion-detail.component';
import { Solucion } from 'app/shared/model/solucion.model';

describe('Component Tests', () => {
  describe('Solucion Management Detail Component', () => {
    let comp: SolucionDetailComponent;
    let fixture: ComponentFixture<SolucionDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ solucion: new Solucion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [SolucionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SolucionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SolucionDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load solucion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.solucion).toEqual(jasmine.objectContaining({ id: 123 }));
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
