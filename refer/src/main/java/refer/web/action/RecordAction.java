package refer.web.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import refer.model.po.Record;
import refer.service.RecordService;

import java.util.List;

/**
 * Created by lv on 2016/12/11.
 */
public class RecordAction extends ActionSupport{
    @Autowired private RecordService recordService;
    private Integer paper_id;
    private Record record;
    private Integer ok;

    public String get(){
        record = recordService.getRecord(paper_id);
        return SUCCESS;
    }

    public String query(){
        try {
            recordService.getRecordFromWos(paper_id);
        }catch (Exception e){
            ok=0;
        }
        return SUCCESS;
    }

    public void setPaper_id(Integer paper_id) {
        this.paper_id = paper_id;
    }

    public Record getRecord() {

        return record;
    }

    public Integer getOk() {
        return ok;
    }
}
