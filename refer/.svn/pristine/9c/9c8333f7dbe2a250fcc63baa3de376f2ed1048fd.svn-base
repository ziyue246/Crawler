package refer.web.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import refer.model.po.Paper;
import refer.service.PaperService;

import java.util.List;

/**
 * Created by Lv on 2016/12/8.
 */
public class PaperAction extends ActionSupport {
    @Autowired private PaperService paperService;
    private Integer report_id;
    private List<Paper> paperlist;

    public String list(){
        paperlist= paperService.list(report_id);
        return SUCCESS;
    }

    public List<Paper> getPaperlist() {
        return paperlist;
    }

    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }
}
