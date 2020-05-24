import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ContenidoService } from 'app/entities/contenido/contenido.service';
import { IContenido, Contenido } from 'app/shared/model/contenido.model';
import { TipoContenido } from 'app/shared/model/enumerations/tipo-contenido.model';

describe('Service Tests', () => {
  describe('Contenido Service', () => {
    let injector: TestBed;
    let service: ContenidoService;
    let httpMock: HttpTestingController;
    let elemDefault: IContenido;
    let expectedResult: IContenido | IContenido[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ContenidoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Contenido(0, 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA', 'AAAAAAA', TipoContenido.VIDEO, false, currentDate);
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

      it('should create a Contenido', () => {
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

        service.create(new Contenido()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Contenido', () => {
        const returnedFromService = Object.assign(
          {
            descripcion: 'BBBBBB',
            videoUrl: 'BBBBBB',
            imagenURl: 'BBBBBB',
            audio: 'BBBBBB',
            tipoContenido: 'BBBBBB',
            activo: true,
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

      it('should return a list of Contenido', () => {
        const returnedFromService = Object.assign(
          {
            descripcion: 'BBBBBB',
            videoUrl: 'BBBBBB',
            imagenURl: 'BBBBBB',
            audio: 'BBBBBB',
            tipoContenido: 'BBBBBB',
            activo: true,
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

      it('should delete a Contenido', () => {
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
