import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FachMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/fach/fach-my-suffix-detail.component';
import { FachMySuffixService } from '../../../../../../main/webapp/app/entities/fach/fach-my-suffix.service';
import { FachMySuffix } from '../../../../../../main/webapp/app/entities/fach/fach-my-suffix.model';

describe('Component Tests', () => {

    describe('FachMySuffix Management Detail Component', () => {
        let comp: FachMySuffixDetailComponent;
        let fixture: ComponentFixture<FachMySuffixDetailComponent>;
        let service: FachMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [FachMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FachMySuffixService,
                    EventManager
                ]
            }).overrideComponent(FachMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FachMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FachMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FachMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fach).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
