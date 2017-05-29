import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ZeugnisMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/zeugnis/zeugnis-my-suffix-detail.component';
import { ZeugnisMySuffixService } from '../../../../../../main/webapp/app/entities/zeugnis/zeugnis-my-suffix.service';
import { ZeugnisMySuffix } from '../../../../../../main/webapp/app/entities/zeugnis/zeugnis-my-suffix.model';

describe('Component Tests', () => {

    describe('ZeugnisMySuffix Management Detail Component', () => {
        let comp: ZeugnisMySuffixDetailComponent;
        let fixture: ComponentFixture<ZeugnisMySuffixDetailComponent>;
        let service: ZeugnisMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [ZeugnisMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ZeugnisMySuffixService,
                    EventManager
                ]
            }).overrideComponent(ZeugnisMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ZeugnisMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZeugnisMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ZeugnisMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.zeugnis).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
