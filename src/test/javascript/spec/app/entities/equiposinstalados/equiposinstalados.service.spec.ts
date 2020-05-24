import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EquiposinstaladosService } from 'app/entities/equiposinstalados/equiposinstalados.service';
import { IEquiposinstalados, Equiposinstalados } from 'app/shared/model/equiposinstalados.model';

describe('Service Tests', () => {
  describe('Equiposinstalados Service', () => {
    let injector: TestBed;
    let service: EquiposinstaladosService;
    let httpMock: HttpTestingController;
    let elemDefault: IEquiposinstalados;
    let expectedResult: IEquiposinstalados | IEquiposinstalados[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EquiposinstaladosService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Equiposinstalados(0, 'AAAAAAA', 'AAAAAAA', currentDate, false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechainstalacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Equiposinstalados', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechainstalacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechainstalacion: currentDate
          },
          returnedFromService
        );

        service.create(new Equiposinstalados()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Equiposinstalados', () => {
        const returnedFromService = Object.assign(
          {
            descripcion: 'BBBBBB',
            observacion: 'BBBBBB',
            fechainstalacion: currentDate.format(DATE_TIME_FORMAT),
            posibleactuliazcion: true,
            cantidad: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechainstalacion: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Equiposinstalados', () => {
        const returnedFromService = Object.assign(
          {
            descripcion: 'BBBBBB',
            observacion: 'BBBBBB',
            fechainstalacion: currentDate.format(DATE_TIME_FORMAT),
            posibleactuliazcion: true,
            cantidad: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechainstalacion: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Equiposinstalados', () => {
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
