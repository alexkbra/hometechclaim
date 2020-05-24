import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { InteresesDetailComponent } from 'app/entities/intereses/intereses-detail.component';
import { Intereses } from 'app/shared/model/intereses.model';

describe('Component Tests', () => {
  describe('Intereses Management Detail Component', () => {
    let comp: InteresesDetailComponent;
    let fixture: ComponentFixture<InteresesDetailComponent>;
    const route = ({ data: of({ intereses: new Intereses(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [InteresesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InteresesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InteresesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load intereses on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.intereses).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
