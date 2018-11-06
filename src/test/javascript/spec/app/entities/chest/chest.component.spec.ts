/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WienerTestModule } from '../../../test.module';
import { ChestComponent } from 'app/entities/chest/chest.component';
import { ChestService } from 'app/entities/chest/chest.service';
import { Chest } from 'app/shared/model/chest.model';

describe('Component Tests', () => {
    describe('Chest Management Component', () => {
        let comp: ChestComponent;
        let fixture: ComponentFixture<ChestComponent>;
        let service: ChestService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WienerTestModule],
                declarations: [ChestComponent],
                providers: []
            })
                .overrideTemplate(ChestComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChestComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChestService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Chest(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.chests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
