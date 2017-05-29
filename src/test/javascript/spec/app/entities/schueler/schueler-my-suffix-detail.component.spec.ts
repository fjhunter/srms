import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SchuelerMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/schueler/schueler-my-suffix-detail.component';
import { SchuelerMySuffixService } from '../../../../../../main/webapp/app/entities/schueler/schueler-my-suffix.service';
import { SchuelerMySuffix } from '../../../../../../main/webapp/app/entities/schueler/schueler-my-suffix.model';

describe('Component Tests', () => {

    describe('SchuelerMySuffix Management Detail Component', () => {
        let comp: SchuelerMySuffixDetailComponent;
        let fixture: ComponentFixture<SchuelerMySuffixDetailComponent>;
        let service: SchuelerMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [SchuelerMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SchuelerMySuffixService,
                    EventManager
                ]
            }).overrideComponent(SchuelerMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchuelerMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchuelerMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SchuelerMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.schueler).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
