package refer.crawler.common;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import refer.crawler.http.HtmlInfo;
import refer.crawler.http.Httpwos;
import refer.model.po.CitedReference;
import refer.model.po.OneStepResponse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author rzy 2016/12/08
 *
 */
public class CrawlerCite {

    public OneStepResponse getCitedReferenceList(String keyword,int startYear,int endYear){

        HtmlInfo html = new HtmlInfo();
        Httpwos wosHttp = new Httpwos();
        OneStepResponse oneStepResponse = new OneStepResponse();

        oneStepResponse.setKeyword(keyword.trim());
        oneStepResponse.setStartYear(startYear);
        oneStepResponse.setEndYear(endYear);

        String  User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
        wosHttp.setKeyword(keyword);
        wosHttp.setStartYear(startYear);
        wosHttp.setEndYear(endYear);
        html.setUa(User_Agent);

        firstPostGet(wosHttp, html);
        wosHttp.extractRecordKeys2Map(html.getContent());
        oneStepResponse.setRecordKeysMap(wosHttp.getRecordKeysMap());
        oneStepResponse.setCookie(html.getCookie());

        List<CitedReference> citeList = new LinkedList<CitedReference>();
        getOnePageCitedReferenceList(html,citeList);
        oneStepResponse.setCitedList(citeList);

        Node parentQidNode = DomTree.getNode(html.getContent(), html.getEncode());
        String parentQidXpath = "//INPUT[@name='parentQid']/@value";
        NodeList parentQidnl = DomTree.commonList(parentQidXpath, parentQidNode);
        if(parentQidnl==null||parentQidnl.item(0)==null)return null;

        String parentQid = parentQidnl.item(0).getTextContent().trim();
        wosHttp.setParentQid(parentQid);
        oneStepResponse.setSid(wosHttp.getSid());
        oneStepResponse.setParentQid(parentQid);

        Node pageCountNode = DomTree.getNode(html.getContent(), html.getEncode());
        int allpage = CitedReferenceXpath.getAllPage(pageCountNode);
        if(allpage>1){
            final String  firstGetUrlModel = "http://apps.webofknowledge.com/summary.do?product=RSCI&" +
                    "parentQid=<parentQid>&" +
                    "SID=<sid>" +
                    "&search_mode=CitedReferenceSearch&formValue(summary_mode)=CitedReferenceSearch&update_back2search_link_param=yes&" +
                    "page=";
            String xpath  =  "//INPUT[@name='parentQid']/@value";
            NodeList nl = DomTree.commonList(xpath, pageCountNode);

            if(nl==null||nl.item(0)==null)return oneStepResponse;
            String qid = nl.item(0).getTextContent().trim();
            wosHttp.setParentQid(qid);
            for (int page = 2; page <= allpage; page++) {
                String url = firstGetUrlModel.replace("<parentQid>",wosHttp.getParentQid())
                        .replace("<sid>",wosHttp.getSid())+page;
                SystemCommon.printLog("第一步第 "+page+" 页get ULR :\n"+url);
                html.setRealUrl(url);
                wosHttp.simpleGet(html);
                wosHttp.extractRecordKeys2Map(html.getContent());
                oneStepResponse.setRecordKeysMap(wosHttp.getRecordKeysMap());
                getOnePageCitedReferenceList(html,citeList);
                oneStepResponse.setCitedList(citeList);
                SystemCommon.sleeps(2);
            }
        }
        return oneStepResponse;
    }
    public void getOnePageCitedReferenceList(HtmlInfo html,List<CitedReference> allCiteList){
        Node node = DomTree.getNode(html.getContent(), html.getEncode());
        List<CitedReference> citeList = new LinkedList<CitedReference>();
        CitedReferenceXpath.getAuthorAbrList(node, citeList);
        CitedReferenceXpath.getAuthorExpList(node, citeList);
        CitedReferenceXpath.getCitedWorkAbrList(node, citeList);
        CitedReferenceXpath.getYearList(node, citeList);
        CitedReferenceXpath.getVolumeList(node, citeList);
        CitedReferenceXpath.getIssueList(node, citeList);
        CitedReferenceXpath.getPageList(node, citeList);
        CitedReferenceXpath.getIdentifierList(node, citeList);
        CitedReferenceXpath.getCitingArticlesList(node, citeList);
        CitedReferenceXpath.getViewRecordTextList(node, citeList);
        CitedReferenceXpath.getViewRecordUrlList(node, citeList);
        for (CitedReference cite:citeList) {

            allCiteList.add(cite);
            cite.setCheckValue(allCiteList.size()+"");
        }
    }
    public void firstPostGet(Httpwos wosHttp,HtmlInfo html)  {
        //http://apps.webofknowledge.com/RSCI_CitedReferenceSearch_input.do?product=RSCI&SID=S2ftWiuu74Usf8Wb6oJ&search_mode=CitedReferenceSearch
        String url = "http://apps.webofknowledge.com/RSCI_CitedReferenceSearch.do";
        //url = "http://apps.webofknowledge.com/RSCI_CitedReferenceSearch_input.do?product=RSCI&SID=S2ftWiuu74Usf8Wb6oJ&search_mode=CitedReferenceSearch";
        Map<String, String> params = new HashMap<String, String>();

        html.setRealUrl(url);

        wosHttp.simplePost(html, params , "01");
        wosHttp.simpleGet(html);
        wosHttp.extractRecordKeys2Map(html.getContent());
    }

    //打印页面所有内容
    public String printAllContent(OneStepResponse oneStepResponse){

        List<String> list = oneStepResponse.getSelectList();
        String keyword = oneStepResponse.getKeyword();
        int startYear = oneStepResponse.getStartYear();
        int endYear = oneStepResponse.getEndYear();
        HtmlInfo html = new HtmlInfo();
        Httpwos wosHttp = new Httpwos();
        //数据恢复
        wosHttp.setSid(oneStepResponse.getSid());
        wosHttp.setParentQid(oneStepResponse.getParentQid());
        wosHttp.setRecordKeysMap(oneStepResponse.getRecordKeysMap());
        wosHttp.setKeyword(keyword);
        wosHttp.setStartYear(startYear);
        wosHttp.setEndYear(endYear);
        String User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
        html.setCookie(oneStepResponse.getCookie());
        html.setUa(User_Agent);
        html.setEncode("utf-8");

        int selectItemPage=0;
        StringBuffer printContentSb = new StringBuffer();
        List<Integer> onePageList = new LinkedList<Integer>();

        String secondUrl = null;
        for (int k=0;k<list.size();k++) {
            int selectItem = Integer.parseInt(list.get(k));

            if((selectItem>selectItemPage*50+50)&&onePageList.size()>0){
                selectItemPage = Integer.parseInt(list.get(k-1))/50+1;
                SystemCommon.printLog("selectItemPage:"+selectItemPage);
                if(secondUrl==null){
                    secondUrl = html.getRealUrl();
                }else{
                    html.setRealUrl(secondUrl+"&page="+selectItemPage);
                }
                secondPPostGet(wosHttp, html,onePageList);

                thirdOncePrint(wosHttp,html,wosHttp.getPrintCount(),printContentSb);
                onePageList.clear();
                SystemCommon.sleeps(2);
            }
            onePageList.add(selectItem);
            if((selectItem==list.size()-1)){
                selectItemPage = Integer.parseInt(list.get(k))/50+1;
                if(secondUrl==null){
                    secondUrl = html.getRealUrl();
                }else{
                    html.setRealUrl(secondUrl+"&page="+selectItemPage);
                }
                secondPPostGet(wosHttp, html,onePageList);
                thirdOncePrint(wosHttp,html,wosHttp.getPrintCount(),printContentSb);
            }
        }
        return printContentSb.toString();

    }
    public void getPrintPageContent(Httpwos wosHttp,HtmlInfo html,StringBuffer sb){
        if(html.getContent()==null)return ;
        String printXpath = "//TABLE/TBODY/TR/TD";
        Node node = DomTree.getNode(html.getContent(), html.getEncode());
        NodeList nl = DomTree.commonList(printXpath, node);
        boolean startMark = false;
        for (int i = 0; i < nl.getLength(); i++) {
            String line = nl.item(i).getTextContent();
            if(line.startsWith("Record 1 ")){
                startMark = true;
            }
            if(startMark) {
                if(line==null||line.length()==0)break;
                //System.out.println(line);
                sb.append(line.trim()+"\n");
            }
        }
    }
    public void secondPPostGet(Httpwos wosHttp,HtmlInfo html, List<Integer> list) {

        if(list==null||list.size()==0)return;

        Map<String, String> params = new HashMap<String, String>();
        String url = "http://apps.webofknowledge.com/fastLaneUpdateSelection.do?cacheurl=no";

        wosHttp.setSelectedIdList(list);
        html.setContent(null);
        html.setRealUrl(url);
        wosHttp.simplePost(html, params , "02");
        url = "http://apps.webofknowledge.com/WOS_CitedRefIndexSearch.do?action=search";
        html.setContent(null);
        html.setRealUrl(url);
        wosHttp.simplePost(html, params , "03");
        //html.setRealUrl(html.getRealUrl());//+"&page=2"
        wosHttp.simpleGet(html);

        Node nodePage = DomTree.getNode(html.getContent(), html.getEncode());
        int pageCount = CitedReferenceXpath.getAllPage(nodePage);
        if(pageCount==0) {
            SystemCommon.printLog("页面为空null");
            return ;
        }
        final String pageUrl = html.getRealUrl();
        if(pageCount>1){
            SystemCommon.sleeps(3);
            html.setRealUrl(pageUrl+"&page="+pageCount);
            wosHttp.simpleGet(html);
        }
        Node pageListNode = DomTree.getNode(html.getContent(), html.getEncode());
        String pageListXpath = "//DIV[@class='search-results']/DIV[@class='search-results-item']";
        NodeList pagenl = DomTree.commonList(pageListXpath, pageListNode);
        int printCount = (pageCount-1)*10+pagenl.getLength();

        SystemCommon.printLog("需要打印的条目为："+printCount);
        wosHttp.setPrintCount(printCount);

        String qidXpath  = "//INPUT[@name='qid']/@value";

        Node qidNode = DomTree.getNode(html.getContent(), html.getEncode());
        NodeList qidNl = DomTree.commonList(qidXpath, qidNode);

        if(qidNl==null||qidNl.item(0)==null)return ;
        String qid = qidNl.item(0).getTextContent().trim();
        wosHttp.setQid(qid);

    }

    public void thirdOncePrint(Httpwos wosHttp,HtmlInfo html,int pageCount,StringBuffer sb){
        for (int i = 0; i*10 < pageCount; i++) {
            SystemCommon.printLog("正在打印第"+(i+1)+"页");
            if(i*10+10<pageCount) {
                thirdPrint(wosHttp, html, i * 10, i * 10 + 10);
            }
            else{
                thirdPrint(wosHttp, html, i * 10, pageCount);
            }
            getPrintPageContent(wosHttp,html,sb);
            //SystemCommon.sleeps(1);
        }
    }
    public void thirdPrint(Httpwos wosHttp,HtmlInfo html,int start,int end) {

        String  selectedIds = "";
        for (int i = start; i < end; i++) {
            if(i==start){
                selectedIds = (i+1)+"";
            }else{
                selectedIds = selectedIds +"%3B"+(i+1);
            }
        }
        String printUrl = "http://apps.webofknowledge.com/OutboundService.do?" +
                "action=go&displayCitedRefs=true&displayTimesCited=true&" +
                "displayUsageInfo=true&viewType=summary&product=RSCI&mark_id=RSCI&colName=RSCI&" +
                "search_mode=CitedRefIndex&locale=en_US&view_name=RSCI-CitedRefIndex-summary&" +
                "sortBy=PY.D%3BLD.D%3BSO.A.ru%3BVL.D%3BPG.A%3BAU.A.ru&mode=outputService&qid="+wosHttp.getQid()+"&" +
                "SID="+wosHttp.getSid()+"&format=formatForPrint&" +
                "filters=CITTIMES+TITLE+SOURCE+ISSN+AUTHORSIDENTIFIERS+ACCESSION_NUM+AUTHORS++&" +
                "selectedIds="+selectedIds+"&mark_to=&mark_from=&" +
                "queryNatural=%3Cb%3ECITED+WORK%3A%3C%2Fb%3E+" +
                "("+wosHttp.getKeyword()+")&count_new_items_marked=0&MaxDataSetLimit=&use_two_ets=false&" +
                "DataSetsRemaining=&IsAtMaxLimit=&IncitesEntitled=no&value(record_select_type)=pagerecords&" +
                "fields_selection=CITTIMES+TITLE+SOURCE+ISSN+AUTHORSIDENTIFIERS+ACCESSION_NUM+AUTHORS++&&&" +
                "totalMarked="+(end-start);

        printUrl = printUrl.replace(" ","%20");

        SystemCommon.printLog("打印链接："+printUrl);
        //printUrl = printUrlModel;
        html.setRealUrl(printUrl);
        wosHttp.simpleGet(html);

        //System.out.println(html.getContent());
    }
}
