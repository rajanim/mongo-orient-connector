package com.bio.cip.indexer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bio.cip.indexer.util.MQUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bio.cip.constants.ApplicationConstants;
import com.bio.cip.constants.FieldNameConstants;
import com.bio.cip.database.MongoDBUtils;
import com.bio.cip.entities.Feed;
import com.bio.cip.entities.WebCrwlCntent;
import com.bio.cip.feed.model.CIPContent;
import com.bio.cip.feed.model.CIPPayload;
import com.bio.cip.feed.model.CIPUser;
import com.bio.cip.indexer.util.AppConfig;
import com.bio.cip.indexer.util.DataCollectionType;
import com.bio.cip.indexer.util.IndexerUtil;
import com.google.gson.Gson;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

/**
 * <p>
 * The Service class manages the indexing of data collected from different data
 * sources and pushes relevant records to mongo
 * </p>
 * 
 * @version 1.0
 * 
 */
public class DataCollectionIndexService {

	private static Logger log = LoggerFactory
			.getLogger(DataCollectionIndexService.class);

	/**
	 * <p>
	 * Method to invoke the respective data collection indexing
	 * </p>
	 * 
	 * @param dcType
	 */
	public static void doIndex(DataCollectionType.DATACOLLECTIONTYPE dcType) {
		CloudSolrServer cloudSolrServer = null;
		HttpSolrServer solrServer = null;
		try {
			switch (dcType) {
			case WEBCRAWL: {
				cloudSolrServer = new CloudSolrServer(
						AppConfig
								.get(ApplicationConstants.SOLR_CLOUD_SERVER_URL));
				cloudSolrServer
						.setDefaultCollection(AppConfig
								.get(ApplicationConstants.SOLR_CLOUD_WEBCRAWL_COLLECTION_NAME));
				execCrawler(cloudSolrServer);

			}
				break;
			case FEED: {
				solrServer = new HttpSolrServer(
						AppConfig
								.get(ApplicationConstants.SOLR_FEEDS_INDEXER_URL));
				execFeed(solrServer);
			}
				break;
			default:
				break;
			}
		} finally {
			if (cloudSolrServer != null)
				cloudSolrServer.shutdown();
			if (solrServer != null)
				solrServer.shutdown();
		}
	}

	/**
	 * <p>
	 * execute indexing of feed records
	 * </p>
	 * 
	 * @param solrServer
	 */
	private static void execFeed(SolrServer solrServer) {
		try {
			List<Feed> feeds = getFeedRecordsFilteredByKeywords(solrServer);

			if (feeds.size() > 0 && pushFeedsToMongo(feeds)) {
				List<String> ids = new ArrayList<>();
				for (Feed feed : feeds)
					ids.add(feed.getFeedUrl());
				solrServer.deleteById(ids);
				solrServer.commit();
			}
		} catch (SolrServerException | IOException e) {
			IndexerUtil.rollBackSolrDelete(e, solrServer);
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			solrServer.shutdown();
		}
	}

	/**
	 * <p>
	 * exec indexing of web content records obtained from crawler
	 * </p>
	 * 
	 * @param solrServer
	 */
	private static void execCrawler(CloudSolrServer cloudSolrServer) {
		try {
			List<WebCrwlCntent> records = getWebCrawlRecordsFilteredByKeywords(cloudSolrServer);

			if (records.size() > 0 && pushWebCrawlRecordsToMongo(records)) {
				List<String> ids = new ArrayList<>();
				for (WebCrwlCntent rec : records)
					ids.add(rec.getId());
				cloudSolrServer.deleteById(ids);
				cloudSolrServer.commit();
			}
		} catch (SolrServerException | IOException e) {
			IndexerUtil.rollBackSolrDelete(e, cloudSolrServer);
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cloudSolrServer.shutdown();
		}

	}

	/**
	 * <p>
	 * Get list of feeds that match the keyword list
	 * </p>
	 * 
	 */
	private static List<Feed> getFeedRecordsFilteredByKeywords(
			SolrServer solrServer) {
		List<Feed> feeds = null;
		try {
			SolrQuery query = new SolrQuery();
			String keyWordQuery = IndexerUtil
					.getKeyWordsQueryString(FieldNameConstants.SOLR_BODY_FIELD_NAME);
			if (!keyWordQuery.isEmpty()) {
				query.setQuery(keyWordQuery);
				query.setRows(ApplicationConstants.GET_MIN_RECORD_COUNT);
				log.info("Query String: " + query.toString());
				QueryResponse response = solrServer.query(query, METHOD.POST);
				feeds = response.getBeans(Feed.class);
				return feeds;
			}

		} catch (SolrServerException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return feeds;

	}

	/**
	 * <p>
	 * To push the relevant feeds matching the keywords to Mongo Staging
	 * Database
	 * </p>
	 * 
	 * @param feeds
	 * @return true : data transfer was success otherwise false.
	 */
	private static boolean pushFeedsToMongo(List<Feed> feeds) {
		MongoDBUtils mongoDbUtils = new MongoDBUtils();
		String dbId = null;
		if (StringUtils.isNotBlank(AppConfig
				.get(ApplicationConstants.STAGING_DATA_BASE_NAME))
				&& (null != feeds)) {
			try {
				DBCollection collection = mongoDbUtils
						.getStaging()
						.getCollection(
								ApplicationConstants.STAGING_DATA_COLLECTION_NAME);
				if (null != collection) {
					for (Feed feed : feeds) {
						DBObject dbObject = convertPojoToDBObject(feed);
						collection.save(dbObject);
						ObjectId id = (ObjectId) dbObject.get("_id");
						dbId = id.toString();
						MQUtil.pushMsg(dbId,
								ApplicationConstants.ENRISH_DATA_QUEUE);
					}
				} else {
					return false;
				}
			} catch (MongoException e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(e);
			} finally {
				mongoDbUtils.disconnect();
			}
		}
		return true;
	}

	public static DBObject convertPojoToDBObject(Feed feed) {
		Gson gson = new Gson();
		CIPPayload rawPayload = new CIPPayload();
		rawPayload = getRawPayloadFromPojo(feed);
		String json = gson.toJson(rawPayload);
		return (DBObject) JSON.parse(json);
	}

	private static CIPPayload getRawPayloadFromPojo(Feed feed) {
		CIPPayload rawPost = new CIPPayload();
		rawPost.setSource(feed.getFeedSource());
		rawPost.setContent(getContentFromFeed(feed));
		rawPost.setUserInfo(getUserInfoFromFeed(feed));
		rawPost.setSystemTime(getSystemTimeStamp());

		return rawPost;
	}

	private static CIPUser getUserInfoFromFeed(Feed feed) {

		CIPUser userInfo = new CIPUser();
		userInfo.setUserName(feed.getFeedUser());

		return userInfo;
	}

	private static CIPContent getContentFromFeed(Feed feed) {
		CIPContent rawFeed = new CIPContent();
		rawFeed.setId(feed.getContentId());
		rawFeed.setContent(feed.getFeedContent());
		rawFeed.setOriginalContent(feed.getOriginalContent());
		rawFeed.setContentUrl(feed.getFeedUrl());
		rawFeed.setTittle(feed.getFeedTitle());
		rawFeed.setFeedSource(feed.getContentSource());
		rawFeed.setTime(feed.getPublishTimeStamp());

		return rawFeed;
	}

	/**
	 * <p>
	 * Get list of feeds that match the keyword list
	 * </p>
	 * 
	 */
	private static List<WebCrwlCntent> getWebCrawlRecordsFilteredByKeywords(
			CloudSolrServer cloudSolrServer) {
		List<WebCrwlCntent> records = null;
		try {
			SolrQuery query = new SolrQuery();
			String keyWordQuery = IndexerUtil
					.getKeyWordsQueryString(FieldNameConstants.SOLR_WEBCONTENT_TEXT_FIELD);
			if (!keyWordQuery.isEmpty()) {
				query.setQuery(keyWordQuery);
				query.setRows(ApplicationConstants.GET_MIN_RECORD_COUNT);
				log.info("Query String: " + query.toString());
				SolrParams params = SolrParams
						.toSolrParams(query.toNamedList());
				QueryResponse response = cloudSolrServer.query(params,
						METHOD.POST);
				records = response.getBeans(WebCrwlCntent.class);
				return records;
			}

		} catch (SolrServerException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return records;

	}

	/**
	 * <p>
	 * To push the relevant feeds matching the keywords to Mongo Staging
	 * Database
	 * </p>
	 * 
	 * @param feeds
	 * @return true : data transfer was success otherwise false.
	 */
	private static boolean pushWebCrawlRecordsToMongo(
			List<WebCrwlCntent> webCntntRecds) {
		MongoDBUtils mongoDbUtils = new MongoDBUtils();
		String dbId = null;
		if (StringUtils.isNotBlank(AppConfig
				.get(ApplicationConstants.STAGING_DATA_BASE_NAME))
				&& (null != webCntntRecds)) {
			try {
				DBCollection collection = mongoDbUtils
						.getStaging()
						.getCollection(
								ApplicationConstants.STAGING_DATA_COLLECTION_NAME);
				if (null != collection) {
					for (WebCrwlCntent webCrwlCntent : webCntntRecds) {
						DBObject dbObject = convertPojoToDBObject(webCrwlCntent);
						collection.save(dbObject);
						ObjectId id = (ObjectId) dbObject.get("_id");
						dbId = id.toString();
						MQUtil.pushMsg(dbId,
								ApplicationConstants.ENRISH_DATA_QUEUE);
					}
				} else {
					return false;
				}
			} catch (MongoException e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(e);
			} finally {
				mongoDbUtils.disconnect();
			}
		}
		return true;
	}

	private static DBObject convertPojoToDBObject(WebCrwlCntent webCrwlCntent) {
		Gson gson = new Gson();
		CIPPayload rawPayload = new CIPPayload();
		rawPayload = getRawPayloadFromPojo(webCrwlCntent);
		String json = gson.toJson(rawPayload);
		return (DBObject) JSON.parse(json);

	}

	private static CIPPayload getRawPayloadFromPojo(WebCrwlCntent webCrwlCntent) {
		CIPPayload rawPost = new CIPPayload();
		rawPost.setSource(webCrwlCntent.getSource());
		CIPContent content = new CIPContent();
		content.setId(webCrwlCntent.getId());
		content.setContent(webCrwlCntent.getContent());
		content.setContentUrl(webCrwlCntent.getUrl());
		content.setTittle(webCrwlCntent.getTitle());
		content.setFeedSource(webCrwlCntent.getSource());
		rawPost.setContent(content);
		rawPost.setSystemTime(getSystemTimeStamp());
		return rawPost;
	}

	/**
	 * method to get current time stamp.
	 * 
	 * @return
	 */
	public static long getSystemTimeStamp() {

		Date curDate = new Date();
		long systemTime = curDate.getTime();
		return systemTime;
	}
}