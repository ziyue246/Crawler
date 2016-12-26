package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import http.HtmlInfo;
import http.Httpieee;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class IeeeMain {

	public static void main(String[] args) {
		IeeeExtract ma = new IeeeExtract();
		String fileName = "config/keyword";
		List<String> kwList = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				kwList.add(line.replace("+", " "));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ma.getIeeeList(kwList);
	}
}
