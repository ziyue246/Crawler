package refer.web.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import refer.model.po.CitedReference;
import refer.model.po.Paper;
import refer.model.po.Record;
import refer.service.CitedService;
import refer.service.PaperService;
import refer.service.RecordService;

import java.util.List;

/**
 * Created by lv on 2016/12/11.
 */
public class ReportAction extends ActionSupport {
    @Autowired
    private PaperService paperService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private CitedService citedService;

    private Integer report_id;
    private List<Paper> paperlist;
    private List<Record> recordlist;
    private List<CitedReference> citedlist;


    public String list(){
        paperlist = paperService.list(report_id);
        recordlist = recordService.list(report_id);
        citedlist = citedService.listSelected(report_id);
        return SUCCESS;
    }


    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }

    public List<Record> getRecordlist() {
        return recordlist;
    }

    public List<Paper> getPaperlist() {
        return paperlist;
    }

    public List<CitedReference> getCitedlist() {
        return citedlist;
    }
}
