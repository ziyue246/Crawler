/**
 * Created by Lv on 2016/11/17.
 */
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'limitWithHtml'})
export class LimitWithHtmlPipe implements PipeTransform {

    transform(value: string, limit:number): any {
        if(value==null) return value;
        if(value.length<=limit)return value;
        limit=this.lengthIncludeTags(value,limit);
        return value.substr(0, limit)+"...";
    }

    //忽略标签后的长度
    lengthIncludeTags(value: string, limit:number): number {
        if(!value.includes("<"))return limit;//没有标签直接返回limit
        let realLength:number =0, seeLength :number =0;
        let start_left=-1,start_right=-1,end_left=-1,end_right=-1;
        while (true) {
            start_left=value.indexOf("<",end_right);
            if(start_left==-1){
                realLength+=limit-seeLength;
                break;
            }
            let tagoutLeng=start_left-(end_right+1);//标签外字符数
            if(seeLength+tagoutLeng>limit){//超出指定长度
                realLength+=limit-seeLength;
                break;
            }else{
                seeLength+=tagoutLeng;
                realLength=start_left;
            }
            start_right=value.indexOf(">",start_left);if(start_right==-1)break;
            end_left=value.indexOf("</",start_right);if(end_left==-1)break;
            end_right=value.indexOf(">",end_left);if(end_right==-1)break;
            let tagInLeng=end_left-(start_right+1);//标签内字符数
            if(seeLength+tagInLeng>limit)//超出指定长度
                break;
            else{
                seeLength+=tagInLeng;
                realLength=end_right+1;
            }
        }
        return realLength;
    }
}