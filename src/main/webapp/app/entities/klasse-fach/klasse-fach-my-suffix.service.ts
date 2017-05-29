import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { KlasseFachMySuffix } from './klasse-fach-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KlasseFachMySuffixService {

    private resourceUrl = 'api/klasse-faches';

    constructor(private http: Http) { }

    create(klasseFach: KlasseFachMySuffix): Observable<KlasseFachMySuffix> {
        const copy = this.convert(klasseFach);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(klasseFach: KlasseFachMySuffix): Observable<KlasseFachMySuffix> {
        const copy = this.convert(klasseFach);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<KlasseFachMySuffix> {
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

    private convert(klasseFach: KlasseFachMySuffix): KlasseFachMySuffix {
        const copy: KlasseFachMySuffix = Object.assign({}, klasseFach);
        return copy;
    }
}
