import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SrmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CreateZeugnisDetailComponent } from '../../../../../../main/webapp/app/entities/create-zeugnis/create-zeugnis-detail.component';
import { CreateZeugnisService } from '../../../../../../main/webapp/app/entities/create-zeugnis/create-zeugnis.service';
import { CreateZeugnis } from '../../../../../../main/webapp/app/entities/create-zeugnis/create-zeugnis.model';

describe('Component Tests', () => {

    describe('CreateZeugnis Management Detail Component', () => {
        let comp: CreateZeugnisDetailComponent;
        let fixture: ComponentFixture<CreateZeugnisDetailComponent>;
        let service: CreateZeugnisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SrmsTestModule],
                declarations: [CreateZeugnisDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CreateZeugnisService,
                    EventManager
                ]
            }).overrideComponent(CreateZeugnisDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CreateZeugnisDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreateZeugnisService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CreateZeugnis(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.createZeugnis).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
