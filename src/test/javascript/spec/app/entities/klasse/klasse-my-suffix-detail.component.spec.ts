import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { KlasseMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/klasse/klasse-my-suffix-detail.component';
import { KlasseMySuffixService } from '../../../../../../main/webapp/app/entities/klasse/klasse-my-suffix.service';
import { KlasseMySuffix } from '../../../../../../main/webapp/app/entities/klasse/klasse-my-suffix.model';

describe('Component Tests', () => {

    describe('KlasseMySuffix Management Detail Component', () => {
        let comp: KlasseMySuffixDetailComponent;
        let fixture: ComponentFixture<KlasseMySuffixDetailComponent>;
        let service: KlasseMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [KlasseMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    KlasseMySuffixService,
                    EventManager
                ]
            }).overrideComponent(KlasseMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KlasseMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KlasseMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new KlasseMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.klasse).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
