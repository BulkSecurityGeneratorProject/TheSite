/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WienerTestModule } from '../../../test.module';
import { MoneyTransferDeleteDialogComponent } from 'app/entities/money-transfer/money-transfer-delete-dialog.component';
import { MoneyTransferService } from 'app/entities/money-transfer/money-transfer.service';

describe('Component Tests', () => {
    describe('MoneyTransfer Management Delete Component', () => {
        let comp: MoneyTransferDeleteDialogComponent;
        let fixture: ComponentFixture<MoneyTransferDeleteDialogComponent>;
        let service: MoneyTransferService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WienerTestModule],
                declarations: [MoneyTransferDeleteDialogComponent]
            })
                .overrideTemplate(MoneyTransferDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MoneyTransferDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoneyTransferService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
