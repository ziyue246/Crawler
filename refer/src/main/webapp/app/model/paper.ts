import {CitedReference} from "./cited-reference";
import {Record} from "./record";
/**
 * Created by Lv on 2016/12/7.
 */
export class Paper{
    public id: number;
    public title: string;
    public citedList: CitedReference[];
    public record: Record;
    public finished: number;

    public showAllAuthors: boolean;
    public showAllWork: boolean;
    public refreshing: boolean;

    constructor(id: number, title: string, citedList: CitedReference[]) {
        this.id = id;
        this.title = title;
        this.citedList = citedList;
    }

}