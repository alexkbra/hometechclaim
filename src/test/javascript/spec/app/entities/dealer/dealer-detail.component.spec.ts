import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HometechclaimTestModule } from '../../../test.module';
import { DealerDetailComponent } from 'app/entities/dealer/dealer-detail.component';
import { Dealer } from 'app/shared/model/dealer.model';

describe('Component Tests', () => {
  describe('Dealer Management Detail Component', () => {
    let comp: DealerDetailComponent;
    let fixture: ComponentFixture<DealerDetailComponent>;
    const route = ({ data: of({ dealer: new Dealer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HometechclaimTestModule],
        declarations: [DealerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DealerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DealerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dealer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dealer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
