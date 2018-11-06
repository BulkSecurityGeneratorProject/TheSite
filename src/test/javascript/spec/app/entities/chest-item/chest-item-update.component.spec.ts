/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { WienerTestModule } from '../../../test.module';
import { ChestItemUpdateComponent } from 'app/entities/chest-item/chest-item-update.component';
import { ChestItemService } from 'app/entities/chest-item/chest-item.service';
import { ChestItem } from 'app/shared/model/chest-item.model';

describe('Component Tests', () => {
    describe('ChestItem Management Update Component', () => {
        let comp: ChestItemUpdateComponent;
        let fixture: ComponentFixture<ChestItemUpdateComponent>;
        let service: ChestItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WienerTestModule],
                declarations: [ChestItemUpdateComponent]
            })
                .overrideTemplate(ChestItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChestItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChestItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ChestItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chestItem = entity;
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
                    const entity = new ChestItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chestItem = entity;
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
