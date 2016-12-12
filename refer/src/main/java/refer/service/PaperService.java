package refer.service;

import org.springframework.stereotype.Service;
import refer.model.po.Paper;

import java.util.List;

/**
 * Created by Lv on 2016/12/8.
 */
@Service
public class PaperService extends BaseService {

    public List<Paper> list(Integer reportId){
        return baseDao.find("from Paper where reportId=?",reportId);
    }
}
