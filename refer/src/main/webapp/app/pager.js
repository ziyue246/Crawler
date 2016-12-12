"use strict";
/**
 * Created by Lv on 2016/11/21.
 */
var Pager = (function () {
    function Pager(completeList, pageSize, rangeLength) {
        this._pageNo = 1;
        this._pageSize = 20;
        this._rangeLength = 15;
        this.completeList = completeList;
        if (pageSize)
            this.pageSize = pageSize;
        if (rangeLength)
            this.rangeLength = rangeLength;
    }
    Object.defineProperty(Pager.prototype, "completeList", {
        get: function () {
            return this._completeList;
        },
        set: function (value) {
            this._completeList = value;
            this.pageNo = 1;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Pager.prototype, "list", {
        get: function () {
            var start = (this.pageNo - 1) * this.pageSize;
            var end = this.pageNo * this.pageSize;
            if (end > this.total)
                end = this.total;
            this._list = this._completeList.slice(start, end);
            return this._list;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Pager.prototype, "pageNo", {
        get: function () {
            if ((this._pageNo - 1) * this._pageSize > this.total)
                this._pageNo = 1;
            return this._pageNo;
        },
        set: function (value) {
            this._pageNo = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Pager.prototype, "pageSize", {
        get: function () {
            return this._pageSize;
        },
        set: function (value) {
            this._pageSize = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Pager.prototype, "total", {
        get: function () {
            this._total = this._completeList.length;
            return this._total;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Pager.prototype, "totalPage", {
        get: function () {
            this._totalPage = Math.floor((this.total + this.pageSize - 1) / this.pageSize);
            return this._totalPage;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Pager.prototype, "rangeLength", {
        get: function () {
            return this._rangeLength;
        },
        set: function (value) {
            this._rangeLength = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Pager.prototype, "range", {
        get: function () {
            this.setStartEnd();
            this._range = [];
            for (var i = this._start; i <= this._end; i++) {
                this._range.push(i);
            }
            return this._range;
        },
        enumerable: true,
        configurable: true
    });
    Pager.prototype.setStartEnd = function () {
        var totalPage = this.totalPage;
        if (totalPage <= this._rangeLength) {
            this._start = 1;
            this._end = totalPage;
        }
        else {
            this._start = this._pageNo - Math.floor((this._rangeLength + 1) / 2) + 1;
            this._end = this._pageNo + Math.floor(this._rangeLength / 2);
            //start、end 修正
            if (this._start < 1) {
                this._end += 1 - this._start;
                this._start = 1;
            }
            if (this._end > totalPage) {
                this._start -= this._end - totalPage;
                this._end = totalPage;
            }
        }
    };
    return Pager;
}());
exports.Pager = Pager;
//# sourceMappingURL=pager.js.map