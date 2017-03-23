import java.io.FileWriter;
import java.io.IOException;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
public class Controller {
		public static void main(String[] args) throws Exception {
	
	// TODO Auto-generated method stub
	
	String crawlStorageFolder = "C:/USC/CSCI 572/Assignment_2/root";
	int numberOfCrawlers = 7;
	int maxDepthOfCrawling=5;
	int maxPagesToFetch=5000;
	int politenessDelay=200;
	
	CrawlConfig config = new CrawlConfig();
	config.setCrawlStorageFolder(crawlStorageFolder);
	config.setMaxDepthOfCrawling(maxDepthOfCrawling);
	config.setMaxPagesToFetch(maxPagesToFetch);
	config.setMaxDownloadSize(999999999);
	config.setIncludeBinaryContentInCrawling(true);
	//config.setPolitenessDelay(politenessDelay);
	/*
	* Instantiate the controller for this crawl.
	*/
	PageFetcher pageFetcher = new PageFetcher(config);
	RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
	RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
	CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
	/*
	* For each crawl, you need to add some seed urls. These are the first
	* URLs that are fetched and then the crawler starts following links
	* which are found in these pages
	*/
	
	controller.addSeed("http://priceschool.usc.edu/");
	/*
	* Start the crawl. This is a blocking operation, meaning that your code
	* 
	* will reach the line after this only when crawling is finished.
	*/
	//MyCrawler.configure(crawlDomains, crawlStorageFolder);
	controller.start(MyCrawler.class, numberOfCrawlers);
	}
	}