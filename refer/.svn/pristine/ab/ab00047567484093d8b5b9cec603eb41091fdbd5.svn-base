/**
 * Created by Lv on 2016/11/21.
 */
export class Pager{
    private _completeList: any[];
    private _list:any[];
    private _pageNo: number= 1;
    private _pageSize: number= 20;
    private _total: number;
    private _totalPage: number;
    private _start: number;
    private _end: number;
    private _rangeLength: number=15;
    private _range: number[];

    constructor (completeList: any[],pageSize?:number,rangeLength?: number){
        this.completeList=completeList;
        if(pageSize)
            this.pageSize=pageSize;
        if(rangeLength)
            this.rangeLength=rangeLength;
    }

    get completeList(): any[] {
        return this._completeList;
    }

    set completeList(value: any[]) {
        this._completeList = value;
        this.pageNo = 1;
    }

    get list(): any[] {
        let start:number = (this.pageNo-1)*this.pageSize;
        let end: number = this.pageNo*this.pageSize;
        if(end>this.total)
            end=this.total;
        this._list=this._completeList.slice(start,end);
        return this._list;
    }

    get pageNo(): number {
        if((this._pageNo-1)*this._pageSize > this.total)
            this._pageNo = 1;
        return this._pageNo;
    }

    set pageNo(value: number) {
        this._pageNo = value;
    }

    get pageSize(): number {
        return this._pageSize;
    }

    set pageSize(value: number) {
        this._pageSize = value;
    }

    get total(): number {
        this._total = this._completeList.length;
        return this._total;
    }

    get totalPage(): number {
        this._totalPage = Math.floor((this.total + this.pageSize - 1) / this.pageSize);
        return this._totalPage;
    }

    get rangeLength(): number {
        return this._rangeLength;
    }

    set rangeLength(value: number) {
        this._rangeLength = value;
    }

    get range(): number[] {
        this.setStartEnd();
        this._range=[];
        for(let i:number=this._start;i<=this._end;i++){
            this._range.push(i);
        }
        return this._range;
    }

    private setStartEnd(): void{
        let totalPage=this.totalPage;
        if (totalPage <= this._rangeLength) {
            this._start = 1;
            this._end = totalPage;
        } else {
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
    }
}