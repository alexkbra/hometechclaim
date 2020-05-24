import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RequerimientoToSolucionService } from 'app/entities/requerimiento-to-solucion/requerimiento-to-solucion.service';
import { IRequerimientoToSolucion, RequerimientoToSolucion } from 'app/shared/model/requerimiento-to-solucion.model';

describe('Service Tests', () => {
  describe('RequerimientoToSolucion Service', () => {
    let injector: TestBed;
    let service: RequerimientoToSolucionService;
    let httpMock: HttpTestingController;
    let elemDefault: IRequerimientoToSolucion;
    let expectedResult: IRequerimientoToSolucion | IRequerimientoToSolucion[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RequerimientoToSolucionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new RequerimientoToSolucion(0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechacreacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RequerimientoToSolucion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechacreacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate
          },
          returnedFromService
        );

        service.create(new RequerimientoToSolucion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RequerimientoToSolucion', () => {
        const returnedFromService = Object.assign(
          {
            fechacreacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RequerimientoToSolucion', () => {
        const returnedFromService = Object.assign(
          {
            fechacreacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a RequerimientoToSolucion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
