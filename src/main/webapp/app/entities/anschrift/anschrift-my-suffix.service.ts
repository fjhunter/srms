import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AnschriftMySuffix } from './anschrift-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AnschriftMySuffixService {

    private resourceUrl = 'api/anschrifts';

    constructor(private http: Http) { }

    create(anschrift: AnschriftMySuffix): Observable<AnschriftMySuffix> {
        const copy = this.convert(anschrift);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(anschrift: AnschriftMySuffix): Observable<AnschriftMySuffix> {
        const copy = this.convert(anschrift);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AnschriftMySuffix> {
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

    private convert(anschrift: AnschriftMySuffix): AnschriftMySuffix {
        const copy: AnschriftMySuffix = Object.assign({}, anschrift);
        return copy;
    }
}
