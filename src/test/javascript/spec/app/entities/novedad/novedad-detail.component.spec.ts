import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HometechclaimTestModule } from '../../../test.module';
import { NovedadDetailComponent } from 'app/entities/novedad/novedad-detail.component';
import { Novedad } from 'app/shared/model/novedad.model';

describe('Component Tests', () => {
  describe('Novedad Management Detail Component', () => {
    let comp: NovedadDetailComponent;
    let fixture: ComponentFixture<NovedadDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ novedad: new Novedad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [NovedadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NovedadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NovedadDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load novedad on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.novedad).toEqual(jasmine.objectContaining({ id: 123 }));
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
