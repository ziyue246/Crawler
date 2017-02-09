package common.download;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.message.BasicNameValuePair;

import common.http.HtmlInfo;

public class DownloadBinary {
	
	
	public static void main2(String[] args) {
		
		HtmlInfo html = new HtmlInfo();
		
		String ua = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2824.2 Safari/537.36";
		
		ua = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729)";
		
		String cookie = "kc_cnki_net_uid=fb5098db-c0b1-99dd-d7e4-fc15d2724431; Ecp_ClientId=2161115085400693876; LID=WEEvREcwSlJHSldRa1FhcEE0L01SRzBvU3dtTnJkU1BJbzlyVGpzK0FNUT0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4ggI8Fm4gTkoUKaID8j8gFw!!; ASPSESSIONIDSASSQRCR=AALGCAFCLOIMEBHDAHINCDNO; SID=088001";
		cookie = "kc_cnki_net_uid=fb5098db-c0b1-99dd-d7e4-fc15d2724431; Ecp_ClientId=2161115085400693876; LID=WEEvREcwSlJHSldRa1FhdkJkcGkzRnBJSVJRQWNTelVsZ2hCT1ZyUmJmZz0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4ggI8Fm4gTkoUKaID8j8gFw!!; c_m_LinID=LinID=WEEvREcwSlJHSldRa1FhdkJkcGkzRnBJSVJRQWNTelVsZ2hCT1ZyUmJmZz0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4ggI8Fm4gTkoUKaID8j8gFw!!&ot=01/12/2017 12:03:51; c_m_expire=2017-01-12 12:03:51; Ecp_LoginStuts={\"IsAutoLogin\":false,\"UserName\":\"zky311044\",\"ShowName\":\"%e4%b8%ad%e5%9b%bd%e7%a7%91%e5%ad%a6%e9%99%a2%e8%87%aa%e5%8a%a8%e5%8c%96%e7%a0%94%e7%a9%b6%e6%89%80\",\"UserType\":\"bk\",\"r\":\"BabUXC\"}";
		
		cookie = "__utmc=198356521; __utmz=198356521.1484792488.1.1.utmccn=(referral)|utmcsr=www2.drugfuture.com|utmcct=/cnpat/search.aspx|utmcmd=referral; CNZZDATA134747=cnzz_eid%3D1981207826-1484788538-http%253A%252F%252Fwww2.drugfuture.com%252F%26ntime%3D1484788538; __utmb=198356521; __utma=198356521.848659685.1484792488.1484792488.1484792488.1";
		
		cookie = "__utmc=198356521; __utmz=198356521.1484792488.1.1.utmccn=(referral)|utmcsr=www2.drugfuture.com|utmcct=/cnpat/search.aspx|utmcmd=referral; CNZZDATA134747=cnzz_eid%3D1981207826-1484788538-http%253A%252F%252Fwww2.drugfuture.com%252F%26ntime%3D1484788538; __utmb=198356521; __utma=198356521.848659685.1484792488.1484792488.1484792488.1";
		
		
		String saveFile = "patent.pdf";
		html.setUa(ua);
		html.setCookie(cookie);
		String orignUrl = "http://caj.d.cnki.net/cjfdsearch/downloadcdmd.asp?encode=gb&nettype=cnet&zt=B016&filename=2VH1GNspVULpXd2E3NvRDTy90VEdzRXJTMIZGTaNDasRGZHhVOTZXZygjctZGe25ER1hkQPZDajR2a1cTQ3YlZNZ1c3p3YXVmM010YpN3dypUcohUdOt2RKtiYFVkdiJnbWhWR=8iMx4GUQt2VGlzVvYWbJ9mRMdETrVUbyV3T2RUc4lkR5ZkbxQHVj9WYVtCcvVUZntmRyYHWxVGVmFkbRdmNC5mNZpnV4dWco9mePdlW2lnQMV1U2FnTxQmZNdnT6NlcRREdNt&doi=CNKI:CDMD:2.2005.098119&filetitle=%bf%bc%c2%c7%c1%b1%b2%bc%b7%b4%b0%fc%b5%c8%d2%f2%cb%d8%d3%b0%cf%ec%b5%c4%b9%f6%b6%af%c2%d6%cc%a5%ce%c2%b6%c8%b3%a1%d3%d0%cf%de%d4%aa%b7%d6%ce%f6_%cb%ce%be%fd%c6%bc&p=cmfd&cflag=&catalog=%d5%aa%d2%aa_3_5_Abstract__%ca%a1%c2%d4_%b9%a5%b6%c1%d1%a7%ce%bb%c6%da%bc%e4%b7%a2%b1%ed%b5%c4%d1%a7%ca%f5%c2%db%ce%c4_97";
		orignUrl = "http://pdf.d.cnki.net/cjfdsearch/pdfdownloadnew.asp?encode=gb&nettype=cnet&zt=C035&filename=Dp1Vkp3b2knNKBXWVJlU2Q3RQlDV340UhpXU2NGRLRjc3sGMpVFSqBjTW9EOKl2MvM3Tj1mbaR1ZsZ2QiFFWKtmZltCUQJ3NvpENqRXekhTMFdTeY1EOl1GVlVXaudVbCZ1d==QPBdzK4FnNNx0LiZWTSJDTWJXQsdXYDtUSP90RO1EdyVTRwZGeLFlU2gmZjVWSLhFVYx0QxlnQGVneyUGbBN2bxdESM9WNwVke1hGdzZzQ3l0RSRlMBN2KLhWZRdjdI9Sb&doi=CNKI:SUN:XJGY.0.2005-02-025&filetitle=%d6%c7%c4%dc%c2%d6%cc%a5%bc%bc%ca%f5%b5%c4%b7%a2%d5%b9%cf%d6%d7%b4%bc%b0%c7%b0%be%b0_%d6%ec%d3%c9%b7%e6&p=cjfq&cflag=&pager=49-52";
		orignUrl = "http://caj.d.cnki.net/cjfdsearch/downloadnew.asp?encode=gb&nettype=cnet&zt=C035&filename=ntkT3NFV08SSXt2R2A3RxBHMOBFb5M0QzE3MuhWQihDcSZXSPVDawQle1IUavN2MoJ0VUpHOHpkTMpVMxlENTdEOaNVSFp3NslXbrYXZvgzT0InaN5mdxhkUxE2QtlmU2FFR==QPVtGM5QEV5UHbQV2a0JVZD1UWxcmWyRXTrAjQSR2TxY3dyRWSvtUM3djQjdjUQt2QFRDUMd3cjdENEZ0QrcmYy1mZvBTQCN3RsBTNtRVOMhXS4ITe0kmT6pmUyczcZp1a&doi=CNKI:SUN:XJGY.0.2005-02-025&filetitle=%d6%c7%c4%dc%c2%d6%cc%a5%bc%bc%ca%f5%b5%c4%b7%a2%d5%b9%cf%d6%d7%b4%bc%b0%c7%b0%be%b0_%d6%ec%d3%c9%b7%e6&p=cjfq&cflag=&pager=49-52";
		
		orignUrl = "http://pdf.d.cnki.net/cjfdsearch/pdfdownloadnew.asp?encode=gb&nettype=cnet&zt=C035&filename=2KwATSJVTeBpmULdDazlkWuBjVvgHRBd0NEVTZ4YFaEVnSx90cqdHZ3JVbWJFc5kXbVNzMspGV5FTaWhjS2JDNrFXYClUb5Ulc6RXeFJVeslWQ0RXUWFzLGtibIxGe0lUe3Z3d=UGNHFWcQJmc5R3aNZVS3dmQY9WQqdnZwJXUOhza4MGVJRGWDZETHR2alNnTQR1K2MnSR1WamxGWIZXYRhESrcUZlhmc3w2bR9iQlRVcuJmZ2tyQPR2TUNmcUhGNSNlY1M1Zvl&doi=CNKI:SUN:QXQC.0.2009-04-015&filetitle=%cc%a5%d1%b9%bc%e0%b2%e2%cf%b5%cd%b3_%cc%e1%b8%df%b0%b2%c8%ab%d0%d4%ba%cd%bd%b5%b5%cdCO_2%c5%c5%b7%c5_%cf%fe%b3%bd&p=cjfq&cflag=&pager=43-44";
		orignUrl = "http://www8.drugfuture.com/cnpat/package/%E5%8F%91%E6%98%8E%E4%B8%93%E5%88%A9%E7%94%B3%E8%AF%B7%E8%AF%B4%E6%98%8E%E4%B9%A6CN201210260819.2.pdf";
		html.setOrignUrl(orignUrl);
		String referUrl = "http://kns.cnki.net/KCMS/detail/detail.aspx?dbcode=CJFQ&dbname=CJFD2009&filename=QXQC200904015&uid=WEEvREcwSlJHSldRa1FhdkJkcGkzRnBJSVJRQWNTelVsZ2hCT1ZyUmJmZz0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4ggI8Fm4gTkoUKaID8j8gFw!!&v=MDYxODhIdGpNcTQ5RVlZUjhlWDFMdXhZUzdEaDFUM3FUcldNMUZyQ1VSTDJmWnVkdEZ5amdVYnJLTkRYYWJiRzQ=";
		
		referUrl = "http://www8.drugfuture.com/cnpat/SecurePdf.aspx";
		html.setReferUrl(referUrl);
		System.out.println(download(html, saveFile));
		System.out.println("Over");
	}
	
	
	
	public static int download(HtmlInfo html ,String saveFile) {
		
		HttpClient client = new HttpClient();
		HttpMethod getMethod = new GetMethod(html.getOrignUrl());
		
		getMethod.setRequestHeader("User-Agent", html.getUa());
		getMethod.setRequestHeader("Connection", "keep-alive");
		if(html.getCookie()!=null){
			getMethod.setRequestHeader("Cookie", html.getCookie());
		}
		if(html.getReferUrl()!=null){
			getMethod.setRequestHeader("Referer",html.getReferUrl());
		}
		
		try {
			client.executeMethod(getMethod);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buffer = new byte[1024];
		byte[] byteArray = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int count = -1;
		InputStream responseBodyAsStream = null;
		
		if (getMethod != null && getMethod.getStatusCode() == 200) {
			try {
				responseBodyAsStream = getMethod.getResponseBodyAsStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//写入页面真实链接的url
			try {
				while ((count = responseBodyAsStream.read(buffer, 0, buffer.length)) > -1) {
					baos.write(buffer, 0, count);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buffer = null;
			try {
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				responseBodyAsStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getMethod.releaseConnection();
			byteArray = baos.toByteArray();
			if(byteArray==null||byteArray.length==0)return -1;
		}
		try {
			FileOutputStream fos = new FileOutputStream(saveFile);
			try {
				fos.write(byteArray);
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public static int post(HtmlInfo html ,ArrayList<String> params) {
		
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(html.getOrignUrl());
		
		postMethod.setRequestHeader("User-Agent", html.getUa());
		postMethod.setRequestHeader("Connection", "keep-alive");
		if(html.getCookie()!=null){
			postMethod.setRequestHeader("Cookie", html.getCookie());
		}
		if(html.getReferUrl()!=null){
			postMethod.setRequestHeader("Referer",html.getReferUrl());
		}
		NameValuePair[] data = new NameValuePair[params.size()];
		for (int i = 0; i < data.length; i++) {
			String[] items = params.get(i).split("##");
			if (items.length == 1) {
				data[0] = (new NameValuePair(items[0], ""));
			} else if (items.length == 2) {
				data[0]  =(new NameValuePair(items[0], items[1]));
			}
		}
		postMethod.setRequestBody(data);
		try {
			client.executeMethod(postMethod);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buffer = new byte[1024];
		byte[] byteArray = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int count = -1;
		InputStream responseBodyAsStream = null;
		
		if (postMethod != null && postMethod.getStatusCode() == 200) {
			try {
				responseBodyAsStream = postMethod.getResponseBodyAsStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//写入页面真实链接的url
			try {
				while ((count = responseBodyAsStream.read(buffer, 0, buffer.length)) > -1) {
					System.out.println(new String(buffer));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buffer = null;
			try {
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				responseBodyAsStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			postMethod.releaseConnection();
			byteArray = baos.toByteArray();
			if(byteArray.length==0)return -1;
		}
		return 0;
	}
}
