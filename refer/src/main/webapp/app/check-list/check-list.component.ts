/**
 * Created by Lv on 2016/12/7.
 */

import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import {Paper} from "../model/paper";
import {CheckListMock} from "../mock/check-list-mock";
import {CheckListService} from "./check-list.service";

@Component({
    selector: 'check-list',
    providers: [CheckListService],
    styleUrls: ['app/check-list/check-list.component.css'],
    templateUrl: `app/check-list/check-list.component.html`
})
export class CheckListComponent implements OnInit{

    private reportId: number;
    private paperList: Paper[];

    private mock: CheckListMock = new CheckListMock();

    constructor (private route: ActivatedRoute, private checkListService: CheckListService) {}

    ngOnInit(): void {
        this.route.params.forEach((params: Params) => {
            let p= params['id'];
            if(p!=null){
                this.reportId=p;
            }
        });
        this.checkListService.getPaperList(this.reportId)
            .then(paperlist=>{
                this.paperList=paperlist;
                this.setCitedListOneByOne();
            });
    }

    setCitedListOneByOne(): void{
        for (var i = 0; i < this.paperList.length; i++) {
            let paper: Paper = this.paperList[i];
            if(paper.citedList==null){
                this.setCitedList(paper)
                    .then(()=>this.setCitedListOneByOne());
            }
        }
    }
    setCitedList(paper: Paper):Promise<Paper> {
        return this.checkListService.getCitedList(paper.id)
            .then(citedlist=>{
                paper.citedList=citedlist;
                return paper;
            })
    }

    refreshCitedList(paper: Paper):Promise<Paper> {
        paper.refreshing=true;   //刷新中
        return this.setCitedList(paper)
            .then(paper=>{
                paper.refreshing=false;   //刷新完成
                return paper;
            })
    }

    query(paper: Paper):Promise<Paper> {
        paper.finished=1;   //检索中
        return this.checkListService.queryRecord(paper.id)
            .then(ok=>{
                if(ok==1){
                    paper.finished=2;   //检索完成
                }else {
                    paper.finished=0;   //检索失败
                }
                return paper;
            })
    }

}