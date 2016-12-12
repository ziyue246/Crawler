/**
 * Created by Lv on 2016/12/8.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {Paper} from "../model/paper";
import {CitedReference} from "../model/cited-reference";
import {Record} from "../model/record";

@Injectable()
export class RecordService {

    private headers = new Headers({'Content-Type': 'application/json'});
    private urlGetRecord = 'http://localhost:8080/refer/json_record_query.action';  // URL to web api
    constructor(private http: Http) { }

    getRecord(paperId: number):Promise<Record>{
        return this.http.post(this.urlGetRecord,JSON.stringify({
            paper_id:paperId
        }),{headers: this.headers}).toPromise()
            .then(response=>response.json().record as Record);
    }


}