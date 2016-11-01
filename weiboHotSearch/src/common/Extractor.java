package common;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Extractor {
	
	public static Set<HotSearch> getHotSearchInfo(String url){
		
		SystemCommon.User_Agent = "";
		SystemCommon.currentCookie = "";
		
		Splider splider = new Splider();
		String content = splider.getHtmlContent(url, url);

		content = DomTree.weiboSearchJsHtml(content);
		Node node=null;
	
		node = DomTree.getNode(content, null);
		NodeList keyWordnl 		= DomTree.commonList(HotSearchXPath.keyWord, node);
		NodeList searchIndexnl 	= DomTree.commonList(HotSearchXPath.searchIndex, node);
		
		Set<HotSearch> hotSearchSet = new HashSet<HotSearch>();
		
		for (int i = 0; i < keyWordnl.getLength(); i++) {
			String keyWord = keyWordnl.item(i).getTextContent().replaceAll(" ", "");
			String searchIndex = searchIndexnl.item(i).getTextContent();
			HotSearch hotSearch = new HotSearch();
			hotSearch.setKeyWord(keyWord);
			hotSearch.setSearchIndex(Integer.parseInt(searchIndex));
			hotSearchSet.add(hotSearch);
		}		
		return hotSearchSet;
	}
}
