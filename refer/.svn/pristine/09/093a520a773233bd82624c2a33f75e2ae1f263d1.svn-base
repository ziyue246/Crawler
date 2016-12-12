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
export class ReportService {

    private headers = new Headers({'Content-Type': 'application/json'});
    private urlGetAllList = 'http://localhost:8080/refer/json_report_list.action';  // URL to web api
    constructor(private http: Http) { }

    getAllList(reportId: number):Promise<any>{
        return this.http.post(this.urlGetAllList,JSON.stringify({
            report_id:reportId
        }),{headers: this.headers}).toPromise()
            .then(response=>response.json());
    }


}