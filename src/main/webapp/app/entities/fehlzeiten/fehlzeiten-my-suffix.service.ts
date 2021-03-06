import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { FehlzeitenMySuffix } from './fehlzeiten-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FehlzeitenMySuffixService {

    private resourceUrl = 'api/fehlzeitens';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(fehlzeiten: FehlzeitenMySuffix): Observable<FehlzeitenMySuffix> {
        const copy = this.convert(fehlzeiten);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(fehlzeiten: FehlzeitenMySuffix): Observable<FehlzeitenMySuffix> {
        const copy = this.convert(fehlzeiten);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<FehlzeitenMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convertItemFromServer(entity: any) {
        entity.datum = this.dateUtils
            .convertDateTimeFromServer(entity.datum);
    }

    private convert(fehlzeiten: FehlzeitenMySuffix): FehlzeitenMySuffix {
        const copy: FehlzeitenMySuffix = Object.assign({}, fehlzeiten);

        copy.datum = this.dateUtils.toDate(fehlzeiten.datum);
        return copy;
    }
}
