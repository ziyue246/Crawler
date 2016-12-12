/**
 * Created by Lv on 2016/12/7.
 */

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import {Record} from "../model/record";
import {ReportService} from "./report.service";
import {Paper} from "../model/paper";
import {CitedReference} from "../model/cited-reference";

@Component({
    selector: 'record',
    providers: [ReportService],
    styleUrls: ['report.component.css'],
    templateUrl: `report`
})
export class ReportComponent implements OnInit{

    private reportId: number;
    private paperlist: Paper[];
    private recordlist: Record[];
    private citedlist: CitedReference[];

    constructor (private route: ActivatedRoute, private recordListService: ReportService) {}

    ngOnInit(): void {
        this.route.params.forEach((params: Params) => {
            let p= params['id'];
            if(p!=null){
                this.reportId=p;
            }
        });
        this.getAllList();
    }

    getAllList(): void{
        this.recordListService.getAllList(this.reportId)
            .then(json=>{
                this.paperlist = json.paperlist;
                this.recordlist = json.recordlist;
                this.citedlist = json.citedlist;
            })
    }

    getCitedList(paper: Paper): CitedReference[]{
        let list: CitedReference[] = [];
        for (var i = 0; i < this.citedlist.length; i++) {
            let cited = this.citedlist[i];
            if(cited.paperId==paper.id){
                list.push(cited);
            }
        }
        return list;
    }

    getRecord(paper: Paper): Record{
        for (var i = 0; i < this.recordlist.length; i++) {
            let record = this.recordlist[i];
            if(record.paperId==paper.id){
                return record;
            }
        }
        return null;
    }

}