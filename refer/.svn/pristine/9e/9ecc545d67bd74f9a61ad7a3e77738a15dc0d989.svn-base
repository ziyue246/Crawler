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
/**
 * Created by Lv on 2016/11/17.
 */
var core_1 = require("@angular/core");
var LimitWithHtmlPipe = (function () {
    function LimitWithHtmlPipe() {
    }
    LimitWithHtmlPipe.prototype.transform = function (value, limit) {
        if (value == null)
            return value;
        if (value.length <= limit)
            return value;
        limit = this.lengthIncludeTags(value, limit);
        return value.substr(0, limit) + "...";
    };
    //忽略标签后的长度
    LimitWithHtmlPipe.prototype.lengthIncludeTags = function (value, limit) {
        if (!value.includes("<"))
            return limit; //没有标签直接返回limit
        var realLength = 0, seeLength = 0;
        var start_left = -1, start_right = -1, end_left = -1, end_right = -1;
        while (true) {
            start_left = value.indexOf("<", end_right);
            if (start_left == -1) {
                realLength += limit - seeLength;
                break;
            }
            var tagoutLeng = start_left - (end_right + 1); //标签外字符数
            if (seeLength + tagoutLeng > limit) {
                realLength += limit - seeLength;
                break;
            }
            else {
                seeLength += tagoutLeng;
                realLength = start_left;
            }
            start_right = value.indexOf(">", start_left);
            if (start_right == -1)
                break;
            end_left = value.indexOf("</", start_right);
            if (end_left == -1)
                break;
            end_right = value.indexOf(">", end_left);
            if (end_right == -1)
                break;
            var tagInLeng = end_left - (start_right + 1); //标签内字符数
            if (seeLength + tagInLeng > limit)
                break;
            else {
                seeLength += tagInLeng;
                realLength = end_right + 1;
            }
        }
        return realLength;
    };
    return LimitWithHtmlPipe;
}());
LimitWithHtmlPipe = __decorate([
    core_1.Pipe({ name: 'limitWithHtml' }),
    __metadata("design:paramtypes", [])
], LimitWithHtmlPipe);
exports.LimitWithHtmlPipe = LimitWithHtmlPipe;
//# sourceMappingURL=limit-with-html.pipe.js.map