import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { SchulformMySuffix } from './schulform-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SchulformMySuffixService {

    private resourceUrl = 'api/schulforms';

    constructor(private http: Http) { }

    create(schulform: SchulformMySuffix): Observable<SchulformMySuffix> {
        const copy = this.convert(schulform);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(schulform: SchulformMySuffix): Observable<SchulformMySuffix> {
        const copy = this.convert(schulform);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<SchulformMySuffix> {
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

    private convert(schulform: SchulformMySuffix): SchulformMySuffix {
        const copy: SchulformMySuffix = Object.assign({}, schulform);
        return copy;
    }
}
