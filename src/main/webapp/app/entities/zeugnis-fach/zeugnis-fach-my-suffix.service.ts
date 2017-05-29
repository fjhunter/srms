import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ZeugnisFachMySuffix } from './zeugnis-fach-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ZeugnisFachMySuffixService {

    private resourceUrl = 'api/zeugnis-faches';

    constructor(private http: Http) { }

    create(zeugnisFach: ZeugnisFachMySuffix): Observable<ZeugnisFachMySuffix> {
        const copy = this.convert(zeugnisFach);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(zeugnisFach: ZeugnisFachMySuffix): Observable<ZeugnisFachMySuffix> {
        const copy = this.convert(zeugnisFach);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ZeugnisFachMySuffix> {
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

    private convert(zeugnisFach: ZeugnisFachMySuffix): ZeugnisFachMySuffix {
        const copy: ZeugnisFachMySuffix = Object.assign({}, zeugnisFach);
        return copy;
    }
}
