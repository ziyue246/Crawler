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
var record_service_1 = require("./record.service");
var RecordComponent = (function () {
    function RecordComponent(route, recordService) {
        this.route = route;
        this.recordService = recordService;
    }
    RecordComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.forEach(function (params) {
            var p = params['id'];
            if (p != null) {
                _this.paperId = p;
            }
        });
        this.getRecord();
    };
    RecordComponent.prototype.getRecord = function () {
        var _this = this;
        this.recordService.getRecord(this.paperId)
            .then(function (record) { return _this.record = record; });
    };
    return RecordComponent;
}());
RecordComponent = __decorate([
    core_1.Component({
        selector: 'record',
        providers: [record_service_1.RecordService],
        styleUrls: ['app/record/record.component.css'],
        templateUrl: "app/record/record.component.html"
    }),
    __metadata("design:paramtypes", [router_1.ActivatedRoute, record_service_1.RecordService])
], RecordComponent);
exports.RecordComponent = RecordComponent;
//# sourceMappingURL=record.component.js.map