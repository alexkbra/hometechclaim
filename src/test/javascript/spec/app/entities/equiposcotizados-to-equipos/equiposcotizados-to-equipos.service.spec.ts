import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EquiposcotizadosToEquiposService } from 'app/entities/equiposcotizados-to-equipos/equiposcotizados-to-equipos.service';
import { IEquiposcotizadosToEquipos, EquiposcotizadosToEquipos } from 'app/shared/model/equiposcotizados-to-equipos.model';

describe('Service Tests', () => {
  describe('EquiposcotizadosToEquipos Service', () => {
    let injector: TestBed;
    let service: EquiposcotizadosToEquiposService;
    let httpMock: HttpTestingController;
    let elemDefault: IEquiposcotizadosToEquipos;
    let expectedResult: IEquiposcotizadosToEquipos | IEquiposcotizadosToEquipos[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EquiposcotizadosToEquiposService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EquiposcotizadosToEquipos(0, 0, 0, 0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechacotizacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EquiposcotizadosToEquipos', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechacotizacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacotizacion: currentDate
          },
          returnedFromService
        );

        service.create(new EquiposcotizadosToEquipos()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EquiposcotizadosToEquipos', () => {
        const returnedFromService = Object.assign(
          {
            valorUnidad: 1,
            valorUtilUnidad: 1,
            descuentoUnidad: 1,
            fechacotizacion: currentDate.format(DATE_TIME_FORMAT),
            cantidadEquipos: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacotizacion: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EquiposcotizadosToEquipos', () => {
        const returnedFromService = Object.assign(
          {
            valorUnidad: 1,
            valorUtilUnidad: 1,
            descuentoUnidad: 1,
            fechacotizacion: currentDate.format(DATE_TIME_FORMAT),
            cantidadEquipos: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacotizacion: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EquiposcotizadosToEquipos', () => {
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
