import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AnschriftMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/anschrift/anschrift-my-suffix-detail.component';
import { AnschriftMySuffixService } from '../../../../../../main/webapp/app/entities/anschrift/anschrift-my-suffix.service';
import { AnschriftMySuffix } from '../../../../../../main/webapp/app/entities/anschrift/anschrift-my-suffix.model';

describe('Component Tests', () => {

    describe('AnschriftMySuffix Management Detail Component', () => {
        let comp: AnschriftMySuffixDetailComponent;
        let fixture: ComponentFixture<AnschriftMySuffixDetailComponent>;
        let service: AnschriftMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [AnschriftMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AnschriftMySuffixService,
                    EventManager
                ]
            }).overrideComponent(AnschriftMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnschriftMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnschriftMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AnschriftMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.anschrift).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
