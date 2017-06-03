import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PrintZeugnisDetailComponent } from '../../../../../../main/webapp/app/entities/print-zeugnis/print-zeugnis-detail.component';
import { PrintZeugnisService } from '../../../../../../main/webapp/app/entities/print-zeugnis/print-zeugnis.service';
import { PrintZeugnis } from '../../../../../../main/webapp/app/entities/print-zeugnis/print-zeugnis.model';

describe('Component Tests', () => {

    describe('PrintZeugnis Management Detail Component', () => {
        let comp: PrintZeugnisDetailComponent;
        let fixture: ComponentFixture<PrintZeugnisDetailComponent>;
        let service: PrintZeugnisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [PrintZeugnisDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PrintZeugnisService,
                    EventManager
                ]
            }).overrideComponent(PrintZeugnisDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrintZeugnisDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrintZeugnisService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PrintZeugnis(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.printZeugnis).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
