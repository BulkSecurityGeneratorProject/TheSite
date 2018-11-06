/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WienerTestModule } from '../../../test.module';
import { ChestItemDetailComponent } from 'app/entities/chest-item/chest-item-detail.component';
import { ChestItem } from 'app/shared/model/chest-item.model';

describe('Component Tests', () => {
    describe('ChestItem Management Detail Component', () => {
        let comp: ChestItemDetailComponent;
        let fixture: ComponentFixture<ChestItemDetailComponent>;
        const route = ({ data: of({ chestItem: new ChestItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WienerTestModule],
                declarations: [ChestItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChestItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChestItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.chestItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
