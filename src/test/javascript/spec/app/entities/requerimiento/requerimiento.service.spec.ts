import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RequerimientoService } from 'app/entities/requerimiento/requerimiento.service';
import { IRequerimiento, Requerimiento } from 'app/shared/model/requerimiento.model';
import { EstadoRequerimiento } from 'app/shared/model/enumerations/estado-requerimiento.model';
import { TipoRequerimiento } from 'app/shared/model/enumerations/tipo-requerimiento.model';

describe('Service Tests', () => {
  describe('Requerimiento Service', () => {
    let injector: TestBed;
    let service: RequerimientoService;
    let httpMock: HttpTestingController;
    let elemDefault: IRequerimiento;
    let expectedResult: IRequerimiento | IRequerimiento[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RequerimientoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Requerimiento(
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        EstadoRequerimiento.INICIADO,
        TipoRequerimiento.NUEVOEQUIPO
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaactualizacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Requerimiento', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaactualizacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate,
            fechaactualizacion: currentDate
          },
          returnedFromService
        );

        service.create(new Requerimiento()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Requerimiento', () => {
        const returnedFromService = Object.assign(
          {
            detalleproblema: 'BBBBBB',
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaactualizacion: currentDate.format(DATE_TIME_FORMAT),
            observaciones: 'BBBBBB',
            idusuarioatendio: 'BBBBBB',
            idusuarioactualizo: 'BBBBBB',
            idUsuario: 'BBBBBB',
            imagen: 'BBBBBB',
            estadoRequerimiento: 'BBBBBB',
            tipoRequerimiento: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate,
            fechaactualizacion: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Requerimiento', () => {
        const returnedFromService = Object.assign(
          {
            detalleproblema: 'BBBBBB',
            fechacreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaactualizacion: currentDate.format(DATE_TIME_FORMAT),
            observaciones: 'BBBBBB',
            idusuarioatendio: 'BBBBBB',
            idusuarioactualizo: 'BBBBBB',
            idUsuario: 'BBBBBB',
            imagen: 'BBBBBB',
            estadoRequerimiento: 'BBBBBB',
            tipoRequerimiento: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechacreacion: currentDate,
            fechaactualizacion: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Requerimiento', () => {
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
