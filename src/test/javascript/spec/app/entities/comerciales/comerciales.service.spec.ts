import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ComercialesService } from 'app/entities/comerciales/comerciales.service';
import { IComerciales, Comerciales } from 'app/shared/model/comerciales.model';

describe('Service Tests', () => {
  describe('Comerciales Service', () => {
    let injector: TestBed;
    let service: ComercialesService;
    let httpMock: HttpTestingController;
    let elemDefault: IComerciales;
    let expectedResult: IComerciales | IComerciales[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ComercialesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Comerciales(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Comerciales', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Comerciales()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Comerciales', () => {
        const returnedFromService = Object.assign(
          {
            codigo: 'BBBBBB',
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            correo: 'BBBBBB',
            zona: 'BBBBBB',
            idciudad: 1,
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

      it('should return a list of Comerciales', () => {
        const returnedFromService = Object.assign(
          {
            codigo: 'BBBBBB',
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            correo: 'BBBBBB',
            zona: 'BBBBBB',
            idciudad: 1,
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

      it('should delete a Comerciales', () => {
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
