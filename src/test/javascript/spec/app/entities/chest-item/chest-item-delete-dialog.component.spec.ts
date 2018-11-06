/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WienerTestModule } from '../../../test.module';
import { ChestItemDeleteDialogComponent } from 'app/entities/chest-item/chest-item-delete-dialog.component';
import { ChestItemService } from 'app/entities/chest-item/chest-item.service';

describe('Component Tests', () => {
    describe('ChestItem Management Delete Component', () => {
        let comp: ChestItemDeleteDialogComponent;
        let fixture: ComponentFixture<ChestItemDeleteDialogComponent>;
        let service: ChestItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WienerTestModule],
                declarations: [ChestItemDeleteDialogComponent]
            })
                .overrideTemplate(ChestItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChestItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChestItemService);
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
