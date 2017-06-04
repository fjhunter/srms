import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Kopfnoten } from './kopfnoten.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KopfnotenService {

    private resourceUrl = 'api/kopfnotens';

    constructor(private http: Http) { }

    create(kopfnoten: Kopfnoten): Observable<Kopfnoten> {
        const copy = this.convert(kopfnoten);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(kopfnoten: Kopfnoten): Observable<Kopfnoten> {
        const copy = this.convert(kopfnoten);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Kopfnoten> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }
    getKopfNoten(id: number): Observable<ResponseWrapper> {
        return this.http.get('api/getKopfnoten/'+ id)
            .map((res: Response) => this.convertResponse(res));
    }
    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(kopfnoten: Kopfnoten): Kopfnoten {
        const copy: Kopfnoten = Object.assign({}, kopfnoten);
        return copy;
    }
}
