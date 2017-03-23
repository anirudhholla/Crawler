
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.http.HttpStatus;

import com.google.common.io.Files;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

	/**
	 * @param args
	 */
	public static long fetches =0;
	public static long fetches_succ =0;
	public static long fetches_aborted =0;
	public static long urls_extracted = 0;
	public static long max_fetch = 0;
	public static HashMap<Integer,Integer> status_map = new HashMap<Integer,Integer>();
	public static HashMap<String,Integer> sizes = new HashMap<String,Integer>();
	public static Set<String> total_unique_url= new HashSet<String>();
	public static Set<String> school_url = new HashSet<String>();
	public static Set<String> usc_url = new HashSet<String>();
	public static Set<String> outside_usc_url = new HashSet<String>();
	public static int dummy = 0;
	private String downloads = "C:/USC/CSCI 572/Assignment_2/data/downloads";
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
			+ "|png|mp4|mp3|zip|gz|jpeg|ppt|pptx))$");
	private static final Pattern docPatterns = Pattern.compile(".*(\\.(pdf|doc|docx|htm))$");

	/**
	 * This method receives two parameters. The first parameter is the page
	 * in which we have discovered this new url and the second parameter is
	 * the new url. You should implement this function to specify whether
	 * the given url should be crawled or not (based on your crawling logic).
	 * In this example, we are instructing the crawler to ignore urls that
	 * have css, js, git, ... extensions and to only accept urls that start
	 * with "http://www.ics.uci.edu/". In this case, we didn't need the
	 * referringPage parameter to make the decision.
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		String arr[] = referringPage.getContentType().split(";");
		//System.out.println(arr[0]);
		String splitString[] = arr[0].split("/");
		String urlText = url.getURL()+",";
		if(url.getURL().contains("priceschool.usc.edu")) {
			//school_s.add(webUrl.getURL());
			urlText+="OK";
		}	
		else if(url.getURL().contains("usc.edu")) {
			//usc_s.add(webUrl.getURL());
			urlText+="USC";
		} 
		else {
			//outside_s.add(webUrl.getURL());
			urlText+="outUSC";
		}
		try
		{
		    FileWriter writer = new FileWriter("C:/USC/CSCI 572/Assignment_2/data/urls.csv",true);
		    writer.append(urlText);
		    writer.append("\n");
		    writer.flush();
		    writer.close();
		    urls_extracted++;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		total_unique_url.add(url.getURL());
		//System.out.println("Pre visit :"+webUrl.getURL());
		if(url.getURL().contains("priceschool.usc.edu")) {
			school_url.add(url.getURL());
		}
		else if(url.getURL().contains("usc.edu") && !url.getURL().contains("priceschool.usc.edu")) {
			usc_url.add(url.getURL());
		}
		else {
			outside_usc_url.add(url.getURL());
		}
		
		//System.out.println("Total Unique url:"+total_unique_url.size());
		//System.out.println("school url:"+school_url.size());
		//System.out.println("usc url:"+usc_url.size());
		//System.out.println("outside url:"+outside_usc_url.size());
	/*	
		if(!(splitString[1].contains("html") || splitString[1].contains("pdf") || splitString[1].contains("msword"))) {
			//System.out.println("Im here");
			return false;
		} */
		
		if (FILTERS.matcher(href).matches()) {
			return false;
		}
		
		if (docPatterns.matcher(href).matches()) {
		//System.out.println("doc" + href);
		return true;
		}
		
		System.out.println(referringPage.getContentType());
		referringPage.setContentEncoding("UTF-8");
		String extension = href.substring(href.lastIndexOf("."));
		System.out.println(extension);
		
		
		if(extension.startsWith(".pdf")){
			String hashedName = Cryptography.MD5(href); 
					//+ extension;
			try {
		          
				OutputStream out = new FileOutputStream("C:/USC/CSCI 572/Assignment_2/download/"+hashedName+".pdf");
				out.write(referringPage.getContentData());
				out.close();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
		
		if(extension.startsWith(".doc")){
			String hashedName = Cryptography.MD5(href); 
					//+ extension;
			try {
		          
				OutputStream out = new FileOutputStream("C:/USC/CSCI 572/Assignment_2/download/"+hashedName+".doc");
				out.write(referringPage.getContentData());
				out.close();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
		if(extension.startsWith(".docx")){
			String hashedName = Cryptography.MD5(href); 
					//+ extension;
			try {
		          
				OutputStream out = new FileOutputStream("C:/USC/CSCI 572/Assignment_2/download/"+hashedName+".docx");
				out.write(referringPage.getContentData());
				out.close();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
			
		return !FILTERS.matcher(href).matches()
				&& href.startsWith("http://priceschool.usc.edu/");
	}

	/**
	 * This function is called when a page is fetched and ready
	 * to be processed by your program.
	 */
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		System.out.println("URL: " + url);

		if(max_fetch>=5000) {
			return;
		}
		max_fetch++;
		
//		System.out.println("URL: " + url+" Status Code : "+page.getStatusCode());
//		write_fetch(url, page.getStatusCode());
		fetches_succ++;
		if (page.getParseData() instanceof HtmlParseData || page.getParseData() instanceof BinaryParseData) {
			
			BinaryParseData binaryParseData;
			HtmlParseData htmlParseData;
			String arr[] = page.getContentType().split(";");
			byte[] x = page.getContentData();
			System.out.println("Number of Bytes : "+x.length);//+" and html :"+y.length);
			Set<WebURL> links;
			String splitString[] = arr[0].split("/");
			if(page.getParseData() instanceof BinaryParseData) {
				binaryParseData = (BinaryParseData) page.getParseData();
				links = binaryParseData.getOutgoingUrls();
//				System.out.println("PDF Data : "+binaryParseData.getHtml());
//				write_document(page.getContentData().toString(),url+".pdf");
				if(!(splitString[1].contains("html") || splitString[1].contains("pdf") || splitString[1].contains("msword") || splitString[1].contains("vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
					//System.out.println("Im here");
					return;
				}
				
				String extension = url.substring(url.lastIndexOf('.'));
				String hashedName = UUID.randomUUID() + extension;
				String filename = downloads + "/" + hashedName;
				/*
				try {
					Files.write(page.getContentData(), new File(filename));
					logger.info("Stored: {}", url);
				} catch (IOException iox) {
					logger.error("Failed to write file: " + filename, iox);
				} */
				
			}
			else {
				htmlParseData = (HtmlParseData) page.getParseData();
				String text = htmlParseData.getText();
				String html = htmlParseData.getHtml();
				links = htmlParseData.getOutgoingUrls();
				System.out.println("Text length: " + text.length());
				System.out.println("Html length: " + html.length());
				System.out.println("Number of outgoing links: " + links.size());
				
				if(!(splitString[1].contains("html") || splitString[1].contains("pdf") || splitString[1].contains("msword") || splitString[1].contains("vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
					//System.out.println("Im here");
					return;
				}
				
				
				String extension = ".html";
				String hashedName = UUID.randomUUID() + extension;
				String filename = downloads + "/" + hashedName;
				/*
				try {
					Files.write(page.getContentData(), new File(filename));
					logger.info("Stored: {}", url);
				} catch (IOException iox) {
					logger.error("Failed to write file: " + filename, iox);
				} */
			}
			write_visit(url,x.length,links.size(),arr[0]);
			System.out.println("Content Type : "+arr[0]);
			System.out.println("Status Code : "+page.getStatusCode());
			
			
			if(x.length<1024) {
				if(!sizes.containsKey("<1KB"))
					sizes.put("<1KB", 1);
				else
					sizes.put("<1KB", sizes.get("<1KB")+1);
			}
			else if(x.length>=1024 && x.length<10240) {
				if(!sizes.containsKey("1KB ~ <10KB"))
					sizes.put("1KB ~ <10KB", 1);
				else
					sizes.put("1KB ~ <10KB", sizes.get("1KB ~ <10KB")+1);
			}
			else if(x.length>=10240 && x.length<102400) {
				if(!sizes.containsKey("10KB ~ <100KB"))
					sizes.put("10KB ~ <100KB", 1);
				else
					sizes.put("10KB ~ <100KB", sizes.get("10KB ~ <100KB")+1);
			}
			else if(x.length>=102400 && x.length<1048576) {
				if(!sizes.containsKey("100KB ~ <1MB"))
					sizes.put("100KB ~ <1MB", 1);
				else
				sizes.put("100KB ~ <1MB", sizes.get("100KB ~ <1MB")+1);
			}
			else {
				if(!sizes.containsKey(">1MB"))
					sizes.put(">1MB", 1);
				else
				sizes.put(">1MB", sizes.get(">1MB")+1);
			}
			for (Map.Entry<String, Integer> entry : sizes.entrySet()) {
			    System.out.println(entry.getKey()+" : "+entry.getValue());
			}
			
		}
		
		
		
	}
	
	@Override
	  protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
			//do nothing
		//-------------------------Statistic nummber 2---------------------------------------
		
				fetches++;
				write_fetch(webUrl.getURL(), statusCode);
				if(statusCode!=HttpStatus.SC_OK) {
					fetches_aborted++;
					
				}
				/*if(statusCode==301) {
					System.out.println(statusCode +" ---- "+ webUrl.getURL());
				}*/
				if(!status_map.containsKey(statusCode))
					status_map.put(statusCode, 1);
				else
					status_map.put(statusCode, status_map.get(statusCode)+1);
				
				for (Map.Entry<Integer, Integer> entry : status_map.entrySet()) {
				    System.out.println(entry.getKey()+" : "+entry.getValue());
				}
				
				//-----------------------------------------------------------------------------------
				
	  }
	@Override
	protected WebURL handleUrlBeforeProcess(WebURL curURL) {
		dummy++;
	    return curURL;
	  }
	
	void write_visit(String webUrl,int x,int links,String type) {
		try
		{
		    FileWriter writer = new FileWriter("C:/USC/CSCI 572/Assignment_2/data/visit.csv",true);
		    writer.append(""+webUrl);
		    writer.append(',');
		    writer.append(""+x);
		    writer.append(',');
		    writer.append(""+links);
		    writer.append(',');
		    writer.append(""+type);
		    writer.append('\n');	
		    writer.flush();
		    writer.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
	}
	
	void write_fetch(String webUrl,int statusCode) {
		try
		{
		    FileWriter writer = new FileWriter("C:/USC/CSCI 572/Assignment_2/data/fetch.csv",true);
		    writer.append(""+webUrl);
		    writer.append(',');
		    writer.append(""+statusCode);
		    writer.append('\n');	
		    writer.flush();
		    writer.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
	}
	
}

