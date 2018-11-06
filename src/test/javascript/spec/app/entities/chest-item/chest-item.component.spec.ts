/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WienerTestModule } from '../../../test.module';
import { ChestItemComponent } from 'app/entities/chest-item/chest-item.component';
import { ChestItemService } from 'app/entities/chest-item/chest-item.service';
import { ChestItem } from 'app/shared/model/chest-item.model';

describe('Component Tests', () => {
    describe('ChestItem Management Component', () => {
        let comp: ChestItemComponent;
        let fixture: ComponentFixture<ChestItemComponent>;
        let service: ChestItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WienerTestModule],
                declarations: [ChestItemComponent],
                providers: []
            })
                .overrideTemplate(ChestItemComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChestItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChestItemService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ChestItem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.chestItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
