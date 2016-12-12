/**
 * Created by Lv on 2016/12/7.
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
var router_1 = require("@angular/router");
var report_service_1 = require("./report.service");
var ReportComponent = (function () {
    function ReportComponent(route, recordListService) {
        this.route = route;
        this.recordListService = recordListService;
    }
    ReportComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.forEach(function (params) {
            var p = params['id'];
            if (p != null) {
                _this.reportId = p;
            }
        });
        this.getAllList();
    };
    ReportComponent.prototype.getAllList = function () {
        var _this = this;
        this.recordListService.getAllList(this.reportId)
            .then(function (json) {
            _this.paperlist = json.paperlist;
            _this.recordlist = json.recordlist;
            _this.citedlist = json.citedlist;
        });
    };
    ReportComponent.prototype.getCitedList = function (paper) {
        var list = [];
        for (var i = 0; i < this.citedlist.length; i++) {
            var cited = this.citedlist[i];
            if (cited.paperId == paper.id) {
                list.push(cited);
            }
        }
        return list;
    };
    ReportComponent.prototype.getRecord = function (paper) {
        for (var i = 0; i < this.recordlist.length; i++) {
            var record = this.recordlist[i];
            if (record.paperId == paper.id) {
                return record;
            }
        }
        return null;
    };
    return ReportComponent;
}());
ReportComponent = __decorate([
    core_1.Component({
        selector: 'record',
        providers: [report_service_1.ReportService],
        styleUrls: ['report.component.css'],
        templateUrl: "report"
    }),
    __metadata("design:paramtypes", [router_1.ActivatedRoute, report_service_1.ReportService])
], ReportComponent);
exports.ReportComponent = ReportComponent;
//# sourceMappingURL=report.component.js.map