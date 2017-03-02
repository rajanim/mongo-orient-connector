package com.bio.cip.indexer.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.bio.cip.constants.ApplicationConstants;
import com.bio.cip.entities.Feed;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bio.cip.indexer.util.AppConfig;

//@RunWith(JUnit4.class)
@Ignore
public class SolrFeedsIndexTest {

	private static SolrServer solrServer;
	private static String testUId = "testUId_1";

	private static Logger log = LoggerFactory
			.getLogger(SolrFeedsIndexTest.class);

	@BeforeClass
	public static void beforeAll() throws IOException, URISyntaxException {
		String urlString = AppConfig
				.get(ApplicationConstants.SOLR_FEEDS_INDEXER_URL);
		solrServer = new HttpSolrServer(urlString);
	}

	@AfterClass
	public static void afterAll() throws IOException, SolrServerException {
		deleteTestRecordFromIndex();
		solrServer.shutdown();
	}

	private static void deleteTestRecordFromIndex() throws IOException,
			SolrServerException {
		solrServer.deleteById(testUId);
	}

	@Test
	public void testIndex() throws Exception {
		try {
			List<Feed> feeds = getFeeds();
			UpdateResponse response = solrServer.addBeans(feeds);
			log.info("Add :" + response.getResponse());
			Assert.assertEquals(0, response.getStatus());
			solrServer.commit();
			log.info("Commit :" + response.getResponse());
			Assert.assertEquals(0, response.getStatus());
		} catch (SolrServerException | IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Test
	public void testSearch() throws IOException, SolrServerException {
		SolrQuery query = new SolrQuery();
		query.setQuery("id:" + testUId);
		QueryResponse response = solrServer.query(query);
		Assert.assertEquals(testUId, response.getResults().get(0)
				.getFieldValue("id"));
	}

	private static List<Feed> getFeeds() {
		List<Feed> feeds = new ArrayList<>();
		/*Feed feed = new Feed(testUId, "test-news", 123456789L,
				"http://test.deccannews.com", "Bangalore Karnatka", "news");*/
		//feeds.add(feed);
		return feeds;
	}
}