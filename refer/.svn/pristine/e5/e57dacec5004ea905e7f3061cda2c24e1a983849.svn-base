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
export class CheckListService {

    private headers = new Headers({'Content-Type': 'application/json'});
    private urlGetPaperList = 'http://localhost:8080/refer/json_paper_list.action';  // URL to web api
    private urlGetCitedList = 'http://localhost:8080/refer/json_cited_list.action';  // URL to web api
    private urlQueryRecord = 'http://localhost:8080/refer/json_record_query.action';  // URL to web api
    constructor(private http: Http) { }

    getPaperList(reportId: number):Promise<Paper[]> {
        return this.http.post(this.urlGetPaperList,JSON.stringify({
                report_id:reportId
                }),{headers: this.headers}).toPromise()
                .then(response=>response.json().paperlist as Paper[]);
    }

    getCitedList(paperId: number):Promise<CitedReference[]> {
        return this.http.post(this.urlGetCitedList,JSON.stringify({
            paper_id:paperId
        }),{headers: this.headers}).toPromise()
            .then(response=>response.json().citedlist as CitedReference[]);
    }

    queryRecord(paperId: number):Promise<number>{
        return this.http.post(this.urlQueryRecord,JSON.stringify({
            paper_id:paperId
        }),{headers: this.headers}).toPromise()
            .then(response=>response.json().ok);
    }


}