import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CreateZeugnis } from './create-zeugnis.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {KlasseMySuffixService} from "../klasse/klasse-my-suffix.service";
import {FachMySuffixService} from "../fach/fach-my-suffix.service";
import {SchuelerMySuffixService} from "../schueler/schueler-my-suffix.service";
import {Zeugnis} from "./zeugnis.model";

@Injectable()
export class ZeugnisService {



    constructor(private http: Http, private klasseService: KlasseMySuffixService, private fachService: FachMySuffixService, private schuelerService: SchuelerMySuffixService) { }



}
