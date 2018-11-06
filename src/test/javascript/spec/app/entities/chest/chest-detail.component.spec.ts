/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WienerTestModule } from '../../../test.module';
import { ChestDetailComponent } from 'app/entities/chest/chest-detail.component';
import { Chest } from 'app/shared/model/chest.model';

describe('Component Tests', () => {
    describe('Chest Management Detail Component', () => {
        let comp: ChestDetailComponent;
        let fixture: ComponentFixture<ChestDetailComponent>;
        const route = ({ data: of({ chest: new Chest(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WienerTestModule],
                declarations: [ChestDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChestDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChestDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.chest).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
