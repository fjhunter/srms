import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FehlzeitenMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/fehlzeiten/fehlzeiten-my-suffix-detail.component';
import { FehlzeitenMySuffixService } from '../../../../../../main/webapp/app/entities/fehlzeiten/fehlzeiten-my-suffix.service';
import { FehlzeitenMySuffix } from '../../../../../../main/webapp/app/entities/fehlzeiten/fehlzeiten-my-suffix.model';

describe('Component Tests', () => {

    describe('FehlzeitenMySuffix Management Detail Component', () => {
        let comp: FehlzeitenMySuffixDetailComponent;
        let fixture: ComponentFixture<FehlzeitenMySuffixDetailComponent>;
        let service: FehlzeitenMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [FehlzeitenMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FehlzeitenMySuffixService,
                    EventManager
                ]
            }).overrideComponent(FehlzeitenMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FehlzeitenMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FehlzeitenMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FehlzeitenMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fehlzeiten).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
