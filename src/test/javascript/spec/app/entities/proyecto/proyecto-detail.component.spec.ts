import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HometechclaimTestModule } from '../../../test.module';
import { ProyectoDetailComponent } from 'app/entities/proyecto/proyecto-detail.component';
import { Proyecto } from 'app/shared/model/proyecto.model';

describe('Component Tests', () => {
  describe('Proyecto Management Detail Component', () => {
    let comp: ProyectoDetailComponent;
    let fixture: ComponentFixture<ProyectoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ proyecto: new Proyecto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [ProyectoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProyectoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProyectoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load proyecto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proyecto).toEqual(jasmine.objectContaining({ id: 123 }));
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
