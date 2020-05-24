import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HometechclaimTestModule } from '../../../test.module';
import { ComercialesDetailComponent } from 'app/entities/comerciales/comerciales-detail.component';
import { Comerciales } from 'app/shared/model/comerciales.model';

describe('Component Tests', () => {
  describe('Comerciales Management Detail Component', () => {
    let comp: ComercialesDetailComponent;
    let fixture: ComponentFixture<ComercialesDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ comerciales: new Comerciales(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [ComercialesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ComercialesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComercialesDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load comerciales on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.comerciales).toEqual(jasmine.objectContaining({ id: 123 }));
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
