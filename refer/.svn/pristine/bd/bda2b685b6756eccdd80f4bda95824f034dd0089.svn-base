package refer.service;

import org.springframework.stereotype.Service;
import refer.model.po.Record;
import refer.model.po.Paper;
import java.util.List;

/**
 * Created by lv on 2016/12/11.
 */
@Service
public class RecordService extends BaseService {

    public List<Record>list(Integer reportId){
        return baseDao.find("from Record where reportId=?", reportId);
    }

    public Record getRecord(Integer paperId){
        Record record = getRecordFromDB(paperId);
        if(record==null){
            record=getRecordFromWos(paperId);
        }
        return record;
    }

    public Record getRecordFromWos(Integer paperId){
        Paper paper = (Paper) baseDao.get(Paper.class,paperId);

        //TODO:读取record接口
        String content="";

        Record record = new Record(content,1,1);
        setPaperInfo(record,paper);
        clearRecord(paperId);
        baseDao.save(record);
        return record;
    }
    public Record getRecordFromDB(Integer paperId){
        List<Record> list = baseDao.find("from Record where paperId=?", paperId);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    private void setPaperInfo(Record record,Paper paper){
        record.setPaperId(paper.getId());
        record.setReportId(paper.getReportId());
    }

    private void clearRecord(Integer paperId){
        baseDao.executeHql("delete from Record where paperId=?",paperId);
    }
    

}
