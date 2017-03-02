package com.bio.cip.indexer.util;



/**
 * <p>
 * </p>
 *
 * @version 1.0
 *
 */
public class DataCollectionType {

	private static final String CRAWLER_SOLR_URL = "";
	private static final String FEED_SOLR_URL = "";

	private DataCollectionType() {
	}

	public enum DATACOLLECTIONTYPE {
		WEBCRAWL(CRAWLER_SOLR_URL), FEED(FEED_SOLR_URL);

		String solrUrl;

		DATACOLLECTIONTYPE(String solrUrl) {
			this.solrUrl = solrUrl;
		}

		String getFileNm() {
			return solrUrl;
		}
	}

}