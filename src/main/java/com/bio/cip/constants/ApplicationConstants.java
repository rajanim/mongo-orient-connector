package com.bio.cip.constants;

public class ApplicationConstants {

	public static final String FEED_INDEXER_JOB = "FEED_INDEXER_JOB";
	public static final String FEED_CLEAR_JOB = "FEED_CLEAR_JOB";
	public static final String SOLR_FEEDS_INDEXER_URL = "solr.index.feeds.url";
	public static final String KEYWORDS_LIST_FILE_PATH = "keywords.full.file.path";

	public static final String SOLR_CONTENT_CORE_URL = "solr.index.contentCore.url";
	public static final String SOLR_EVENT_CORE_URL = "solr.index.eventCore.url";
	public static final String SOLR_PERSON_CORE_URL = "solr.index.personCore.url";
	public static final String SOLR_LOCATION_CORE_URL = "solr.index.locationCore.url";
	public static final String SOLR_ORGANIZATION_CORE_URL = "solr.index.organizationCore.url";

	public static final String REP_INTERVAL_FOR_ENTITIES_INDEXING = "repeat.interval.entity.indexing";
	public static final String REP_INTERVAL_FOR_DATA_COLLECTION_INDEXING="repeat.interval.data.collection.indexing";
	public static final String REP_INTERVAL_FOR_FEEDS_ENTITY_INDEXING = "repeat.interval.feed.entity.indexing";
	public static final String REP_INTERVAL_FOR_CLEAR_INDEX = "repeat.interval.clear.indexes";

	public static final String DB_ADDRESS = "mongo.db.ip";
	public static final String DB_PORT = "mongo.db.port";
	public static final String STAGING_DATA_BASE_NAME = "mongo.staging.db.name";
	public static final String STAGING_DATA_COLLECTION_NAME = "staging-data";
	public static final int GET_MIN_RECORD_COUNT = 100;
	public static final String ACTIVE_MQ_URL = "failover://tcp://10.20.26.65:61616?wireFormat.maxInactivityDurationInitalDelay=30000";
	public static final String ENRISH_DATA_QUEUE = "enrich-dataQ";
	public static final String SOLR_CLOUD_SERVER_URL = "solrcloud.zookeeper.server.url";
	public static final String SOLR_CLOUD_WEBCRAWL_COLLECTION_NAME = "solrcloud.webcrawl.collection.name";
	public static final int INDEX_COMMIT_LIMIT = 500;
}
