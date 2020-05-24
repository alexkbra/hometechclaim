import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CotizacionService } from 'app/entities/cotizacion/cotizacion.service';
import { ICotizacion, Cotizacion } from 'app/shared/model/cotizacion.model';
import { Estadocotizacion } from 'app/shared/model/enumerations/estadocotizacion.model';

describe('Service Tests', () => {
  describe('Cotizacion Service', () => {
    let injector: TestBed;
    let service: CotizacionService;
    let httpMock: HttpTestingController;
    let elemDefault: ICotizacion;
    let expectedResult: ICotizacion | ICotizacion[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CotizacionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Cotizacion(0, currentDate, 'AAAAAAA', 0, 0, 0, currentDate, false, 0, Estadocotizacion.INICIO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            fechamantenimiento: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Cotizacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            fechamantenimiento: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate,
            fechamantenimiento: currentDate
          },
          returnedFromService
        );

        service.create(new Cotizacion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Cotizacion', () => {
        const returnedFromService = Object.assign(
          {
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            descripcion: 'BBBBBB',
            valoriva: 1,
            totalsiniva: 1,
            porcentajedescuento: 1,
            fechamantenimiento: currentDate.format(DATE_TIME_FORMAT),
            activo: true,
            total: 1,
            estadocotizacion: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate,
            fechamantenimiento: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Cotizacion', () => {
        const returnedFromService = Object.assign(
          {
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            descripcion: 'BBBBBB',
            valoriva: 1,
            totalsiniva: 1,
            porcentajedescuento: 1,
            fechamantenimiento: currentDate.format(DATE_TIME_FORMAT),
            activo: true,
            total: 1,
            estadocotizacion: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate,
            fechamantenimiento: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Cotizacion', () => {
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
