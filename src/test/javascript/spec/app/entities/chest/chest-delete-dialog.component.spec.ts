/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WienerTestModule } from '../../../test.module';
import { ChestDeleteDialogComponent } from 'app/entities/chest/chest-delete-dialog.component';
import { ChestService } from 'app/entities/chest/chest.service';

describe('Component Tests', () => {
    describe('Chest Management Delete Component', () => {
        let comp: ChestDeleteDialogComponent;
        let fixture: ComponentFixture<ChestDeleteDialogComponent>;
        let service: ChestService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WienerTestModule],
                declarations: [ChestDeleteDialogComponent]
            })
                .overrideTemplate(ChestDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChestDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChestService);
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
