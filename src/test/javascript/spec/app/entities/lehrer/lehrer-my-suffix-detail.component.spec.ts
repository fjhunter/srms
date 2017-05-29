import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LehrerMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/lehrer/lehrer-my-suffix-detail.component';
import { LehrerMySuffixService } from '../../../../../../main/webapp/app/entities/lehrer/lehrer-my-suffix.service';
import { LehrerMySuffix } from '../../../../../../main/webapp/app/entities/lehrer/lehrer-my-suffix.model';

describe('Component Tests', () => {

    describe('LehrerMySuffix Management Detail Component', () => {
        let comp: LehrerMySuffixDetailComponent;
        let fixture: ComponentFixture<LehrerMySuffixDetailComponent>;
        let service: LehrerMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [LehrerMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LehrerMySuffixService,
                    EventManager
                ]
            }).overrideComponent(LehrerMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LehrerMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LehrerMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LehrerMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.lehrer).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
