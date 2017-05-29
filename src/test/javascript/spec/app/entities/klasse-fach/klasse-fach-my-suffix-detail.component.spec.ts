import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { KlasseFachMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/klasse-fach/klasse-fach-my-suffix-detail.component';
import { KlasseFachMySuffixService } from '../../../../../../main/webapp/app/entities/klasse-fach/klasse-fach-my-suffix.service';
import { KlasseFachMySuffix } from '../../../../../../main/webapp/app/entities/klasse-fach/klasse-fach-my-suffix.model';

describe('Component Tests', () => {

    describe('KlasseFachMySuffix Management Detail Component', () => {
        let comp: KlasseFachMySuffixDetailComponent;
        let fixture: ComponentFixture<KlasseFachMySuffixDetailComponent>;
        let service: KlasseFachMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [KlasseFachMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    KlasseFachMySuffixService,
                    EventManager
                ]
            }).overrideComponent(KlasseFachMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KlasseFachMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KlasseFachMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new KlasseFachMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.klasseFach).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
