/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { WienerTestModule } from '../../../test.module';
import { MoneyTransferUpdateComponent } from 'app/entities/money-transfer/money-transfer-update.component';
import { MoneyTransferService } from 'app/entities/money-transfer/money-transfer.service';
import { MoneyTransfer } from 'app/shared/model/money-transfer.model';

describe('Component Tests', () => {
    describe('MoneyTransfer Management Update Component', () => {
        let comp: MoneyTransferUpdateComponent;
        let fixture: ComponentFixture<MoneyTransferUpdateComponent>;
        let service: MoneyTransferService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WienerTestModule],
                declarations: [MoneyTransferUpdateComponent]
            })
                .overrideTemplate(MoneyTransferUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MoneyTransferUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoneyTransferService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MoneyTransfer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.moneyTransfer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MoneyTransfer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.moneyTransfer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
