package common;

import java.util.List;
import java.util.concurrent.Executors;

import common.system.AppContext;
import common.system.Job;
import common.system.Systemconfig;
import common.util.StringUtil;
import common.util.TimeUtil;

public class WOSStart {

	public static void main(String[] args) throws Exception {
		AppContext.initAppCtx("");

		for (String site : Systemconfig.allSiteinfos.keySet()) {
			create(site);
		}
		List<String> list = StringUtil.contentList("config/journal.txt");
		if (Systemconfig.style) {
			run(list);
		} else {
			Job job = new Job();
			// job.list("wos", "");
			job.pageSingle("a", "wos");
		}
	}

	public static void create(String site) {
		int num = Systemconfig.allSiteinfos.get(site).getThreadNum();
		Systemconfig.metaexec.put(site, Executors.newFixedThreadPool(num > 3 ? 3 : num));
		Systemconfig.dataexec.put(site, Executors.newFixedThreadPool(num > 5 ? 5 : num));
	}

	private static void run(List<String> list) {
		while (true) {
			Job job = new Job();
			List<String> crawledPapers = Systemconfig.mysqlService.getAllTitle(Systemconfig.table);
			for (String s : list) {
				String title = s.split(":")[0].replace(":", "").replace(",", "");
				if (crawledPapers.contains(title)) {
					System.err.println("已采集过: " + title);
					continue;
				}
				if (Systemconfig.finish.get("wos" + s) == null || Systemconfig.finish.get("wos" + s)) {
					job.list("wos", s);
					TimeUtil.rest(5);
					Systemconfig.finish.put("wos" + s, false);
				}
				TimeUtil.rest(5);
			}
			TimeUtil.rest(15);
		}
	}

}
