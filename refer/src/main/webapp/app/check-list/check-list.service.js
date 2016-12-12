/**
 * Created by Lv on 2016/12/8.
 */
"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var http_1 = require("@angular/http");
require("rxjs/add/operator/toPromise");
var CheckListService = (function () {
    function CheckListService(http) {
        this.http = http;
        this.headers = new http_1.Headers({ 'Content-Type': 'application/json' });
        this.urlGetPaperList = 'http://localhost:8080/refer/json_paper_list.action'; // URL to web api
        this.urlGetCitedList = 'http://localhost:8080/refer/json_cited_list.action'; // URL to web api
        this.urlQueryRecord = 'http://localhost:8080/refer/json_record_query.action'; // URL to web api
    }
    CheckListService.prototype.getPaperList = function (reportId) {
        return this.http.post(this.urlGetPaperList, JSON.stringify({
            report_id: reportId
        }), { headers: this.headers }).toPromise()
            .then(function (response) { return response.json().paperlist; });
    };
    CheckListService.prototype.getCitedList = function (paperId) {
        return this.http.post(this.urlGetCitedList, JSON.stringify({
            paper_id: paperId
        }), { headers: this.headers }).toPromise()
            .then(function (response) { return response.json().citedlist; });
    };
    CheckListService.prototype.queryRecord = function (paperId) {
        return this.http.post(this.urlQueryRecord, JSON.stringify({
            paper_id: paperId
        }), { headers: this.headers }).toPromise()
            .then(function (response) { return response.json().ok; });
    };
    return CheckListService;
}());
CheckListService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], CheckListService);
exports.CheckListService = CheckListService;
//# sourceMappingURL=check-list.service.js.map