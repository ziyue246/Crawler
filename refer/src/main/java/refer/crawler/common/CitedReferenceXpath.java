

package refer.crawler.common;


import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import refer.model.po.CitedReference;

import java.util.List;


/**
 * Created by rzy on 2016/12/8.
 */
public class CitedReferenceXpath {

    //private static String lenth="//TR/TD[@class='citedRefTableRow1'][1]";
    private static String authorAbr="//TR/TD[contains(@class,'citedRefTableRow')][1]";
    private static String authorExp="//TR/TD[contains(@class,'citedRefTableRow')][1]";
    private static String citedWorkAbr="//TR/TD[contains(@class,'citedRefTableRow')][2]";
    private static String citedWorkExp="//TR/TD[contains(@class,'citedRefTableRow')][2]";
    private static String year="//TR/TD[contains(@class,'citedRefTableRow')][3]";
    private static String volume="//TR/TD[contains(@class,'citedRefTableRow')][4]";
    private static String issue="//TR/TD[contains(@class,'citedRefTableRow')][5]";
    private static String page="//TR/TD[contains(@class,'citedRefTableRow')][6]";
    private static String identifier="//TR/TD[contains(@class,'citedRefTableRow')][7]";
    private static String citingArticles="//TR/TD[contains(@class,'citedRefTableRow')][8]";
    private static String viewRecordText="//TR/TD[contains(@class,'citedRefTableRow')][9]";
    private static String viewRecordUrl="//TR/TD[contains(@class,'citedRefTableRow')][9]";
    private static String viewRecordPreUrl="http://apps.webofknowledge.com/";
    private static String allPage="//DIV[@class='summVCRnav']//SPAN[@id='pageCount.bottom']";

    public static void getAuthorAbrList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(authorAbr, node);
        if(nl==null)return;
        for (int i = 0; i < nl.getLength(); i++) {

            CitedReference cite = new CitedReference();
            String []result = {""};
            getNodeContent(nl.item(i),"name","author_abr",true,result);
            String s = result[0].trim();
            cite.setAuthorAbr(s);

            citeList.add(cite);
        }
    }
    public static void getAuthorExpList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(authorExp, node);
        if(nl==null)return;
        for (int i = 0; i < citeList.size(); i++) {

            CitedReference cite = citeList.get(i);
            String []result = {""};
            getNodeContent(nl.item(i),"name","author_exp",true,result);
            String s = result[0].trim();
            cite.setAuthorExp(s);
        }
    }
    public static void getCitedWorkAbrList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(citedWorkAbr, node);
        if(nl==null)return;
        for (int i = 0; i < citeList.size(); i++) {

            String []result = {""};
            getNodeContent(nl.item(i),"class","cited_work_abbrev",true,result);
            String s = result[0].trim();
            citeList.get(i).setCitedWorkAbr(s);
        }
    }
    public static void getCitedWorkExpList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(citedWorkExp, node);
        if(nl==null)return;
        for (int i = 0; i < citeList.size(); i++) {
            String []result = {""};
            getNodeContent(nl.item(i),"class","cited_work_exp",true,result);
            String s = result[0].trim();
            citeList.get(i).setCitedWorkExp(s);
        }
    }
    public static void getYearList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(year, node);
        if(nl==null)return;
        for (int i = 0; i < citeList.size(); i++) {
            String s = nl.item(i).getTextContent().trim();
            citeList.get(i).setYear(s);
        }
    }
    public static void getVolumeList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(volume, node);
        if(nl==null)return;
        for (int i = 0; i < citeList.size(); i++) {
            String s = nl.item(i).getTextContent().trim();
            citeList.get(i).setVolume(s);
        }
    }
    public static void getIssueList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(issue, node);
        if(nl==null)return;
        for (int i = 0; i < citeList.size(); i++) {
            String s = nl.item(i).getTextContent().trim();
            citeList.get(i).setIssue(s);
        }
    }
    public static void getPageList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(page, node);
        if(nl==null)return;
        for (int i = 0; i < citeList.size(); i++) {
            String s = nl.item(i).getTextContent().trim();
            citeList.get(i).setPage(s);
        }
    }
    public static void getIdentifierList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(identifier, node);
        if(nl==null)return;
        for (int i = 0; i < citeList.size(); i++) {
            String s = nl.item(i).getTextContent().trim();
            citeList.get(i).setIdentifier(s);
        }
    }
    public static void getCitingArticlesList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(citingArticles, node);
        if(nl==null)return;
        for (int i = 0; i < citeList.size(); i++) {
            String s = nl.item(i).getTextContent().trim();
            citeList.get(i).setCitingArticles(s);
        }
    }
    public static void getViewRecordTextList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(viewRecordText, node);
        if(nl==null)return;
        for (int i = 0; i < citeList.size(); i++) {
            String []result = {""};
            getNodeContent(nl.item(i),"title","",false,result);
            String s = result[0].trim();
            citeList.get(i).setViewRecordText(s);
        }
    }
    public static void getViewRecordUrlList(Node node,List<CitedReference> citeList){

        NodeList nl = DomTree.commonList(viewRecordUrl, node);
        if(nl==null)return;
        for (int i = 0; i < citeList.size(); i++) {
            String []result = {""};
            getNodeContent(nl.item(i),"href","",false,result);
            String s = result[0].trim();
            if(s!=null&&s.length()>0) {
                citeList.get(i).setViewRecordUrl(viewRecordPreUrl + s);
            }
        }
    }
    public static int getAllPage(Node node){

        NodeList nl = DomTree.commonList(allPage, node);
        if(nl==null)return 0;
        for (int i = 0; i < nl.getLength(); i++) {
            String s = nl.item(i).getTextContent().trim();
            return Integer.parseInt(s);
        }
        return 0;
    }
    public static void getNodeContent(Node node,String name,String value,boolean mark,String []result){
        if(mark){
            NamedNodeMap al =  node.getAttributes();
            if(al!=null) {
                for (int j = 0; j < al.getLength(); j++) {
                    if(al.item(j).getNodeName().equals(name)&&
                            al.item(j).getNodeValue().startsWith(value)){
                        if(result[0].length()==0){
                            result[0]=node.getTextContent();
                        }
                    }
                }
            }
            NodeList nl = node.getChildNodes();
            if(nl==null)return ;
            for (int i = 0; i < nl.getLength(); i++) {
                getNodeContent(nl.item(i),name,value,mark,result);
            }
        }else{
            NamedNodeMap al =  node.getAttributes();
            if(al!=null) {
                for (int j = 0; j < al.getLength(); j++) {
                    if(name.equals(al.item(j).getNodeName())){
                        if(result[0].length()==0){
                            result[0]=al.item(j).getNodeValue();
                        }
                    }

                }
            }
            NodeList nl = node.getChildNodes();
            if(nl==null)return ;
            for (int i = 0; i < nl.getLength(); i++) {
                getNodeContent(nl.item(i),name,value,mark,result);
            }
        }
    }
}
