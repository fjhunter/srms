import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CreateZeugnis } from './create-zeugnis.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CreateZeugnisService {

    private resourceUrl = 'api/create-zeugnis';

    constructor(private http: Http) { }

    create(createZeugnis: CreateZeugnis): Observable<CreateZeugnis> {
        const copy = this.convert(createZeugnis);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(createZeugnis: CreateZeugnis): Observable<CreateZeugnis> {
        const copy = this.convert(createZeugnis);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CreateZeugnis> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(createZeugnis: CreateZeugnis): CreateZeugnis {
        const copy: CreateZeugnis = Object.assign({}, createZeugnis);
        return copy;
    }
}
