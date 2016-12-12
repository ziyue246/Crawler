package refer.service;

import org.springframework.stereotype.Service;
import refer.crawler.common.CrawlerCite;
import refer.model.po.CitedReference;
import refer.model.po.OneStepResponse;
import refer.model.po.Paper;
import refer.model.po.Report;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Lv on 2016/12/8.
 */
@Service
public class CitedService extends BaseService {
    private final Integer EXPIRYTIME=3600;
    private CrawlerCite crawlerCite= new CrawlerCite();

    public List<CitedReference>listSelected(Integer reportId){
        return  baseDao.find("from CitedReference where reportId=? and selected=1",reportId);
    }

    public List<CitedReference>getCited(Integer paperId){
        Paper paper = (Paper) baseDao.get(Paper.class,paperId);
        if (paper.getExpiryTime()==null||paper.getExpiryTime().before(new Date())){
            return getCitedFromWos(paperId);
        }else {
            List<CitedReference> list = getCitedFromDB(paperId);
            if(list.isEmpty()){
                list=getCitedFromWos(paperId);
            }
            return list;
        }
    }

    public List<CitedReference>getCitedFromWos(Integer paperId){
        Paper paper = (Paper) baseDao.get(Paper.class,paperId);
        Report report = (Report) baseDao.get(Report.class,paper.getReportId());

        OneStepResponse oneStepResponse = crawlerCite.getCitedReferenceList(paper.getTitle(),report.getBeginYear(),report.getEndYear());

        List<CitedReference> list = oneStepResponse.getCitedList();
        setPaperInfo(list,paper);
        clearCited(paperId);
        saveAllCited(list);

        //设置paper的expiryTime
        paper.setExpiryTime(expiryTime());
        baseDao.save(paper);
        return list;
    }
    public List<CitedReference>getCitedFromDB(Integer paperId){
        return baseDao.find("from CitedReference where paperId=?",paperId);
    }

    private void setPaperInfo(List<CitedReference>list,Paper paper){
        for (CitedReference citedReference : list) {
            citedReference.setPaperId(paper.getId());
            citedReference.setReportId(paper.getReportId());
        }
    }
    private void saveAllCited(List<CitedReference>list){
        for (CitedReference citedReference : list) {
            baseDao.save(citedReference);
        }
    }
    private void clearCited(Integer paperId){
        baseDao.executeHql("delete from CitedReference where paperId=?",paperId);
    }
    private Timestamp expiryTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,EXPIRYTIME);
        return new Timestamp(calendar.getTime().getTime());
    }
}
