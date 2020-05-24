import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { NotificacionesService } from 'app/entities/notificaciones/notificaciones.service';
import { INotificaciones, Notificaciones } from 'app/shared/model/notificaciones.model';
import { Areas } from 'app/shared/model/enumerations/areas.model';

describe('Service Tests', () => {
  describe('Notificaciones Service', () => {
    let injector: TestBed;
    let service: NotificacionesService;
    let httpMock: HttpTestingController;
    let elemDefault: INotificaciones;
    let expectedResult: INotificaciones | INotificaciones[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NotificacionesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Notificaciones(0, 'AAAAAAA', Areas.SRVICIOALCLIENTE, false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Notificaciones', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Notificaciones()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Notificaciones', () => {
        const returnedFromService = Object.assign(
          {
            correo: 'BBBBBB',
            area: 'BBBBBB',
            activo: true,
            idusuario: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Notificaciones', () => {
        const returnedFromService = Object.assign(
          {
            correo: 'BBBBBB',
            area: 'BBBBBB',
            activo: true,
            idusuario: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Notificaciones', () => {
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
