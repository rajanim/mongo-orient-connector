package com.bio.cip.indexer.service;

import java.io.IOException;

import com.bio.cip.constants.FieldNameConstants;
import com.bio.cip.indexer.util.DataCollectionType;
import com.bio.cip.indexer.util.IndexerUtil;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bio.cip.constants.ApplicationConstants;
import com.bio.cip.indexer.util.AppConfig;

public class DataColnClearRecordsService {
	/**
	 * Logger object
	 */
	private static Logger log = LoggerFactory
			.getLogger(DataColnClearRecordsService.class);

	/**
	 * <p>
	 * Method to invoke the respective data collection indexing
	 * </p>
	 * 
	 * @param dcType
	 */
	public static void clear(DataCollectionType.DATACOLLECTIONTYPE dcType) {
		CloudSolrServer cloudSolrServer = null;
		try {
			switch (dcType) {
			case WEBCRAWL: {
				cloudSolrServer = new CloudSolrServer(
						AppConfig
								.get(ApplicationConstants.SOLR_CLOUD_SERVER_URL));
				cloudSolrServer
						.setDefaultCollection(AppConfig
								.get(ApplicationConstants.SOLR_CLOUD_WEBCRAWL_COLLECTION_NAME));
				clearIndexesNotMatchingKW(cloudSolrServer,
						FieldNameConstants.SOLR_WEBCONTENT_TEXT_FIELD);

			}
				break;
			case FEED: {
				clearIndexesNotMatchingKW(
						AppConfig
								.get(ApplicationConstants.SOLR_FEEDS_INDEXER_URL),
						FieldNameConstants.SOLR_BODY_FIELD_NAME);

			}
				break;
			default:
				break;
			}
		} finally {
			if (cloudSolrServer != null)
				cloudSolrServer.shutdown();
		}
	}

	/**
	 * <p>
	 * Method that performs delete feeds did not match to keywords
	 * </p>
	 * 
	 */
	public static void clearIndexesNotMatchingKW(String solrUrl,
			String fieldName) {
		SolrServer solrServer = null;
		try {
			solrServer = new HttpSolrServer(solrUrl);
			String keyWordQuery = IndexerUtil
					.getNotMatchingKeyWordsQuery(fieldName);
			if (!keyWordQuery.isEmpty()) {
				log.info(keyWordQuery);
				solrServer.deleteByQuery(keyWordQuery);
				solrServer.commit();
			}
		} catch (SolrServerException | IOException e) {
			IndexerUtil.rollBackSolrDelete(e, solrServer);
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			if (solrServer != null)
				solrServer.shutdown();
		}
	}

	/**
	 * <p>
	 * Method that performs delete records on webcontent solr cloud did not
	 * match to keywords
	 * </p>
	 * 
	 */
	public static void clearIndexesNotMatchingKW(
			CloudSolrServer cloudSolrServer, String fieldName) {
		try {
			String keyWordQuery = IndexerUtil
					.getNotMatchingKeyWordsQuery(fieldName);
			if (!keyWordQuery.isEmpty()) {
				log.info(keyWordQuery);
				cloudSolrServer.deleteByQuery(keyWordQuery);
				cloudSolrServer.commit();
			}
		} catch (SolrServerException | IOException e) {
			IndexerUtil.rollBackSolrDelete(e, cloudSolrServer);
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

}
