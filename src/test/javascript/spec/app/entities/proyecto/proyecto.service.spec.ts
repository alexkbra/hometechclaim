import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProyectoService } from 'app/entities/proyecto/proyecto.service';
import { IProyecto, Proyecto } from 'app/shared/model/proyecto.model';

describe('Service Tests', () => {
  describe('Proyecto Service', () => {
    let injector: TestBed;
    let service: ProyectoService;
    let httpMock: HttpTestingController;
    let elemDefault: IProyecto;
    let expectedResult: IProyecto | IProyecto[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProyectoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Proyecto(0, currentDate, 'AAAAAAA', 0, 0, currentDate, 0, 0, false, 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaultimomantenimiento: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Proyecto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaultimomantenimiento: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate,
            fechaultimomantenimiento: currentDate
          },
          returnedFromService
        );

        service.create(new Proyecto()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Proyecto', () => {
        const returnedFromService = Object.assign(
          {
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            descripcion: 'BBBBBB',
            valoriva: 1,
            totalsiniva: 1,
            fechaultimomantenimiento: currentDate.format(DATE_TIME_FORMAT),
            porcentajedescuento: 1,
            total: 1,
            activo: true,
            imagen: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate,
            fechaultimomantenimiento: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Proyecto', () => {
        const returnedFromService = Object.assign(
          {
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            descripcion: 'BBBBBB',
            valoriva: 1,
            totalsiniva: 1,
            fechaultimomantenimiento: currentDate.format(DATE_TIME_FORMAT),
            porcentajedescuento: 1,
            total: 1,
            activo: true,
            imagen: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate,
            fechaultimomantenimiento: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Proyecto', () => {
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
