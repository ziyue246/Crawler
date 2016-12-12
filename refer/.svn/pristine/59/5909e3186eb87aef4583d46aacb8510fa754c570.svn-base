/**
 * Created by Lv on 2016/12/7.
 */

import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import {Record} from "../model/record";
import {RecordService} from "./record.service";

@Component({
    selector: 'record',
    providers: [RecordService],
    styleUrls: ['app/record/record.component.css'],
    templateUrl: `app/record/record.component.html`
})
export class RecordComponent implements OnInit{

    private paperId: number;
    private record: Record;

    constructor (private route: ActivatedRoute, private recordService: RecordService) {}

    ngOnInit(): void {
        this.route.params.forEach((params: Params) => {
            let p= params['id'];
            if(p!=null){
                this.paperId=p;
            }
        });
        this.getRecord();
    }

    getRecord(): void{
        this.recordService.getRecord(this.paperId)
            .then(record=>this.record = record)
    }

}