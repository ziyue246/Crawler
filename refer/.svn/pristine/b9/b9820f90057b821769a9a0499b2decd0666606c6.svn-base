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
var check_list_mock_1 = require("../mock/check-list-mock");
var check_list_service_1 = require("./check-list.service");
var CheckListComponent = (function () {
    function CheckListComponent(route, checkListService) {
        this.route = route;
        this.checkListService = checkListService;
        this.mock = new check_list_mock_1.CheckListMock();
    }
    CheckListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.forEach(function (params) {
            var p = params['id'];
            if (p != null) {
                _this.reportId = p;
            }
        });
        this.checkListService.getPaperList(this.reportId)
            .then(function (paperlist) {
            _this.paperList = paperlist;
            _this.setCitedListOneByOne();
        });
    };
    CheckListComponent.prototype.setCitedListOneByOne = function () {
        var _this = this;
        for (var i = 0; i < this.paperList.length; i++) {
            var paper = this.paperList[i];
            if (paper.citedList == null) {
                this.setCitedList(paper)
                    .then(function () { return _this.setCitedListOneByOne(); });
            }
        }
    };
    CheckListComponent.prototype.setCitedList = function (paper) {
        return this.checkListService.getCitedList(paper.id)
            .then(function (citedlist) {
            paper.citedList = citedlist;
            return paper;
        });
    };
    CheckListComponent.prototype.refreshCitedList = function (paper) {
        paper.refreshing = true; //刷新中
        return this.setCitedList(paper)
            .then(function (paper) {
            paper.refreshing = false; //刷新完成
            return paper;
        });
    };
    CheckListComponent.prototype.query = function (paper) {
        paper.finished = 1; //检索中
        return this.checkListService.queryRecord(paper.id)
            .then(function (ok) {
            if (ok == 1) {
                paper.finished = 2; //检索完成
            }
            else {
                paper.finished = 0; //检索失败
            }
            return paper;
        });
    };
    return CheckListComponent;
}());
CheckListComponent = __decorate([
    core_1.Component({
        selector: 'check-list',
        providers: [check_list_service_1.CheckListService],
        styleUrls: ['app/check-list/check-list.component.css'],
        templateUrl: "app/check-list/check-list.component.html"
    }),
    __metadata("design:paramtypes", [router_1.ActivatedRoute, check_list_service_1.CheckListService])
], CheckListComponent);
exports.CheckListComponent = CheckListComponent;
//# sourceMappingURL=check-list.component.js.map