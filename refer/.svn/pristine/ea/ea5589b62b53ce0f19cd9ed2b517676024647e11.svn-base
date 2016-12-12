package refer.web.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import refer.model.po.CitedReference;
import refer.service.CitedService;

import java.util.List;

/**
 * Created by Lv on 2016/12/8.
 */
public class CitedAction extends ActionSupport {
    @Autowired private CitedService citedService;

    private Integer paper_id;
    private List<CitedReference> citedlist;

    public String list(){
        citedlist= citedService.getCited(paper_id);
        return SUCCESS;
    }

    public void setPaper_id(Integer paper_id) {
        this.paper_id = paper_id;
    }

    public List<CitedReference> getCitedlist() {
        return citedlist;
    }
}
