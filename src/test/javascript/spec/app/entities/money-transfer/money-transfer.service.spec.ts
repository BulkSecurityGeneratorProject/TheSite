/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MoneyTransferService } from 'app/entities/money-transfer/money-transfer.service';
import { IMoneyTransfer, MoneyTransfer } from 'app/shared/model/money-transfer.model';

describe('Service Tests', () => {
    describe('MoneyTransfer Service', () => {
        let injector: TestBed;
        let service: MoneyTransferService;
        let httpMock: HttpTestingController;
        let elemDefault: IMoneyTransfer;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(MoneyTransferService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new MoneyTransfer(0, 0, currentDate, 'AAAAAAA', false, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        payedTime: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a MoneyTransfer', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        payedTime: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        payedTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new MoneyTransfer(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a MoneyTransfer', async () => {
                const returnedFromService = Object.assign(
                    {
                        payedAmount: 1,
                        payedTime: currentDate.format(DATE_FORMAT),
                        payedInCurrency: 'BBBBBB',
                        paymentSuccessful: true,
                        paymentMode: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        payedTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of MoneyTransfer', async () => {
                const returnedFromService = Object.assign(
                    {
                        payedAmount: 1,
                        payedTime: currentDate.format(DATE_FORMAT),
                        payedInCurrency: 'BBBBBB',
                        paymentSuccessful: true,
                        paymentMode: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        payedTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a MoneyTransfer', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
