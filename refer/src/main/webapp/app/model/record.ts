/**
 * Created by Lv on 2016/12/7.
 */
export class Record{
    public id: number;
    public content: string;
    public paperId: number;
    public reportId: number;

    constructor(id: number, content: string, paperId: number, reportId: number) {
        this.id = id;
        this.content = content;
        this.paperId = paperId;
        this.reportId = reportId;
    }
}