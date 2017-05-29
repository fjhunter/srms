import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SchulformMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/schulform/schulform-my-suffix-detail.component';
import { SchulformMySuffixService } from '../../../../../../main/webapp/app/entities/schulform/schulform-my-suffix.service';
import { SchulformMySuffix } from '../../../../../../main/webapp/app/entities/schulform/schulform-my-suffix.model';

describe('Component Tests', () => {

    describe('SchulformMySuffix Management Detail Component', () => {
        let comp: SchulformMySuffixDetailComponent;
        let fixture: ComponentFixture<SchulformMySuffixDetailComponent>;
        let service: SchulformMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [SchulformMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SchulformMySuffixService,
                    EventManager
                ]
            }).overrideComponent(SchulformMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchulformMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchulformMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SchulformMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.schulform).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
