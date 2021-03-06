import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { KlasseMySuffix } from './klasse-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KlasseMySuffixService {

    private resourceUrl = 'api/klasses';

    constructor(private http: Http) { }

    create(klasse: KlasseMySuffix): Observable<KlasseMySuffix> {
        const copy = this.convert(klasse);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(klasse: KlasseMySuffix): Observable<KlasseMySuffix> {
        const copy = this.convert(klasse);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<KlasseMySuffix> {
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

    private convert(klasse: KlasseMySuffix): KlasseMySuffix {
        const copy: KlasseMySuffix = Object.assign({}, klasse);
        return copy;
    }
}
