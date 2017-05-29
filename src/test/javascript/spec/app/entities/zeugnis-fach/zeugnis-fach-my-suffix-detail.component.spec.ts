import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ZeugnisFachMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/zeugnis-fach/zeugnis-fach-my-suffix-detail.component';
import { ZeugnisFachMySuffixService } from '../../../../../../main/webapp/app/entities/zeugnis-fach/zeugnis-fach-my-suffix.service';
import { ZeugnisFachMySuffix } from '../../../../../../main/webapp/app/entities/zeugnis-fach/zeugnis-fach-my-suffix.model';

describe('Component Tests', () => {

    describe('ZeugnisFachMySuffix Management Detail Component', () => {
        let comp: ZeugnisFachMySuffixDetailComponent;
        let fixture: ComponentFixture<ZeugnisFachMySuffixDetailComponent>;
        let service: ZeugnisFachMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [ZeugnisFachMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ZeugnisFachMySuffixService,
                    EventManager
                ]
            }).overrideComponent(ZeugnisFachMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ZeugnisFachMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZeugnisFachMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ZeugnisFachMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.zeugnisFach).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
