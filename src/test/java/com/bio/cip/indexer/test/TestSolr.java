package com.bio.cip.indexer.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestSolr {

	private SolrServer server;

	/**
	 * setup.
	 */
	@Before
	public void setup() {
		server = new HttpSolrServer("http://10.20.26.177:8080/solr-feed/core0");
		try {
			server.deleteByQuery("id:id1");
			server.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test Adding.
	 * 
	 * @throws MalformedURLException
	 *             error
	 */
	@Test
	public void testAdding() throws MalformedURLException {
		try {
			final SolrInputDocument doc1 = new SolrInputDocument();
			doc1.addField("id", "id1", 1.0f);

			final Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			docs.add(doc1);

			server.add(docs);
			server.commit();

			final SolrQuery query = new SolrQuery();
			query.setQuery("*:*");
			final QueryResponse rsp = server.query(query);

			final SolrDocumentList solrDocumentList = rsp.getResults();
			for (final SolrDocument doc : solrDocumentList) {
				String id = (String) doc.getFieldValue("id");
				System.out.println(" id:" + id);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@After
	public void afterAll() throws IOException, SolrServerException {
		server.deleteById("id:id1");
		server.commit();
		server.shutdown();
	}

}
