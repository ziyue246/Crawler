/**
 * Created by Lv on 2016/12/7.
 */
export class CitedReference{
    public id: number;
    public checkId: string;
    public authorAbr: string;
    public authorExp: string;
    public citedWorkAbr: string;
    public citedWorkExp: string;
    public year: string;
    public volume: string;
    public issue: string;
    public page: string;
    public identifier: string;
    public citingArticles: string;
    public viewRecordText: string;
    public viewRecordUrl: string;
    public paperId: number;
    public reportId: number;
    public selected: number;

    constructor(id: number, checkId: string, authorAbr: string, authorExp: string, citedWorkAbr: string, citedWorkExp: string, year: string, volume: string, issue: string, page: string, identifier: string, citingArticles: string, viewRecordText: string, viewRecordUrl: string, paperId: number, reportId: number, selected: number) {
        this.id = id;
        this.checkId = checkId;
        this.authorAbr = authorAbr;
        this.authorExp = authorExp;
        this.citedWorkAbr = citedWorkAbr;
        this.citedWorkExp = citedWorkExp;
        this.year = year;
        this.volume = volume;
        this.issue = issue;
        this.page = page;
        this.identifier = identifier;
        this.citingArticles = citingArticles;
        this.viewRecordText = viewRecordText;
        this.viewRecordUrl = viewRecordUrl;
        this.paperId = paperId;
        this.reportId = reportId;
        this.selected = selected;
    }
}