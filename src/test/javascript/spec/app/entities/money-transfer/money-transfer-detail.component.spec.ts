/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WienerTestModule } from '../../../test.module';
import { MoneyTransferDetailComponent } from 'app/entities/money-transfer/money-transfer-detail.component';
import { MoneyTransfer } from 'app/shared/model/money-transfer.model';

describe('Component Tests', () => {
    describe('MoneyTransfer Management Detail Component', () => {
        let comp: MoneyTransferDetailComponent;
        let fixture: ComponentFixture<MoneyTransferDetailComponent>;
        const route = ({ data: of({ moneyTransfer: new MoneyTransfer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WienerTestModule],
                declarations: [MoneyTransferDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MoneyTransferDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MoneyTransferDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.moneyTransfer).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
