package com.bio.cip.indexer.service;

import java.io.IOException;

import com.bio.cip.indexer.util.IndexerUtil;
import com.bio.cip.indexer.util.WikiUtil;
import com.bio.cip.indexer.vo.WikiOrganizationVO;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bio.cip.constants.ApplicationConstants;
import com.bio.cip.constants.FieldNameConstants;
import com.bio.cip.constants.QueryConstants;
import com.bio.cip.indexer.util.AppConfig;
import com.bio.cip.indexer.util.OrientDBUtil;
import com.bio.cip.indexer.util.RIDUtil;
import com.bio.cip.indexer.util.RIDUtil.RIDTYPE;
import com.bio.cip.indexer.vo.Content;
import com.bio.cip.indexer.vo.Event;
import com.bio.cip.indexer.vo.Location;
import com.bio.cip.indexer.vo.Organization;
import com.bio.cip.indexer.vo.Person;
import com.orientechnologies.orient.core.command.OCommandResultListener;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLAsynchQuery;

/**
 * <p>
 * The Service class that manages the actual indexing of data from Orient
 * </p>
 * 
 * @version 1.0
 * 
 */
public class IndexerService {

	private static Logger log = LoggerFactory.getLogger(IndexerService.class);

	/**
	 * These static Variables are to maintain the pointer to know till which rid
	 * of OrientdB, the data is fetched.
	 */
	static String curContentRid = "";
	static String curEventRid = "";
	static String curPersonRid = "";
	static String curLocationRid = "";
	static String curOrgRid = "";

	/**
	 * <p>
	 * Method to invoke the respective entities indexing
	 * </p>
	 * 
	 * @param ridType
	 */
	public static void doIndex(RIDUtil.RIDTYPE ridType) {
		ODatabaseDocumentTx db = null;
		try {
			db = OrientDBUtil.connect(AppConfig.getDBCreds());
			String MAX_RID = maxRid(ridType);
			log.info(" doIndex method, ridType: " + ridType + "  Max_Rid:"
					+ MAX_RID);
			switch (ridType) {
			case CONTENT:
				 execContent(db, MAX_RID, getSolrUrl(ridType));
				break;
			case EVENT:
				 execEvent(db, MAX_RID, getSolrUrl(ridType));
				break;
			case PERSON:
				 execPerson(db, MAX_RID, getSolrUrl(ridType));
				break;
			case LOCATION:
				 execLocation(db, MAX_RID, getSolrUrl(ridType));
				break;
			case ORGANIZATION:
				execOrganization(db, MAX_RID, getSolrUrl(ridType));
				break;
			default:
				break;
			}
		} finally {
			OrientDBUtil.disconnect(db);
		}
	}

	/**
	 * <p>
	 * Method to get the Solr Server instance for respective cores-entities
	 * </p>
	 * 
	 * @param ridType
	 * @return SolrServer
	 */
	private static SolrServer getSolrUrl(RIDTYPE ridType) {
		SolrServer solrServer = null;
		switch (ridType) {
		case CONTENT:
			solrServer = new HttpSolrServer(
					AppConfig.get(ApplicationConstants.SOLR_CONTENT_CORE_URL));
			break;
		case EVENT:
			solrServer = new HttpSolrServer(
					AppConfig.get(ApplicationConstants.SOLR_EVENT_CORE_URL));
			break;
		case PERSON:
			solrServer = new HttpSolrServer(
					AppConfig.get(ApplicationConstants.SOLR_PERSON_CORE_URL));
			break;
		case LOCATION:
			solrServer = new HttpSolrServer(
					AppConfig.get(ApplicationConstants.SOLR_LOCATION_CORE_URL));
			break;
		case ORGANIZATION:
			solrServer = new HttpSolrServer(
					AppConfig
							.get(ApplicationConstants.SOLR_ORGANIZATION_CORE_URL));
			break;
		default:
			// TODO
			break;
		}
		return solrServer;
	}

	private static String maxRid(RIDUtil.RIDTYPE ridType) {
		return RIDUtil.readRid(ridType);
	}

	/**
	 * <p>
	 * To get the content records from orientDb and index to Solr ContentCore
	 * </p>
	 * 
	 * @param db
	 * @param rid
	 * @param solrServer
	 */
	private static void execContent(ODatabaseDocumentTx db, final String rid,
			final SolrServer solrServer) {
		curContentRid = rid;
		db.command(
				new OSQLAsynchQuery<ODocument>(QueryConstants.QRY_CONTENT,
						new OCommandResultListener() {
							int count = 0;

							public boolean result(Object record) {
								try {
									solrServer
											.addBean(getContentDocument((ODocument) record));
									count++;
									if (count > ApplicationConstants.INDEX_COMMIT_LIMIT) {
										solrServer.commit();
										RIDUtil.writeRid(RIDTYPE.CONTENT, curContentRid);
										count = 0;
									}
								} catch (IOException | SolrServerException e) {
									IndexerUtil.solrRollBack(solrServer);
									log.error(e.getMessage(), e);
									throw new RuntimeException(e);
								}
								return true;
							}

							public void end() {
								log.info("End of Content Indexing. Encountered EOF @"
										+ System.currentTimeMillis()
										+ "curContentRid: " + curContentRid);
								IndexerUtil
										.solrServerCommitShutDown(solrServer);
								RIDUtil.writeRid(RIDTYPE.CONTENT, curContentRid);
							}

						})).execute(rid);
	}

	/**
	 * <p>
	 * To get the event records from orientDb and index to Solr eventCore
	 * </p>
	 * 
	 * @param db
	 * @param rid
	 * @param solrServer
	 */
	private static void execEvent(ODatabaseDocumentTx db, final String rid,
			final SolrServer solrServer) {
		curEventRid = rid;
		db.command(
				new OSQLAsynchQuery<ODocument>(QueryConstants.QRY_EVENT,
						new OCommandResultListener() {
							int count = 0;

							public boolean result(Object record) {
								try {
									solrServer
											.addBean(getEventDocument((ODocument) record));
									log.info("curEventRIid: " + curEventRid);
									count++;
									if (count > ApplicationConstants.INDEX_COMMIT_LIMIT) {
										solrServer.commit();
										RIDUtil.writeRid(RIDTYPE.EVENT, curEventRid);
										count = 0;
									}
								} catch (IOException | SolrServerException e) {
									IndexerUtil.solrRollBack(solrServer);
									log.error(e.getMessage(), e);
									throw new RuntimeException(e);
								}
								return true;
							}

							public void end() {
								log.info("Event Indexing encountered EOF @"
										+ System.currentTimeMillis());
								IndexerUtil
										.solrServerCommitShutDown(solrServer);
								RIDUtil.writeRid(RIDTYPE.EVENT, curEventRid);
							}
						})).execute(rid);
	}

	/**
	 * <p>
	 * To get the person records from orientDb and index to Solr personCore
	 * </p>
	 * 
	 * @param db
	 * @param rid
	 * @param solrServer
	 */
	private static void execPerson(ODatabaseDocumentTx db, final String rid,
			final SolrServer solrServer) {
		curPersonRid = rid;
		db.command(
				new OSQLAsynchQuery<ODocument>(QueryConstants.QRY_PERSON,
						new OCommandResultListener() {
							int count = 0;

							public boolean result(Object record) {
								try {
									solrServer
											.addBean(getPersonDocument((ODocument) record));
									log.info("curPersonRid: " + curPersonRid);
									count++;
									if (count > ApplicationConstants.INDEX_COMMIT_LIMIT) {
										solrServer.commit();
										RIDUtil.writeRid(RIDTYPE.PERSON, curPersonRid);
										count = 0;
									}
								} catch (IOException | SolrServerException e) {
									IndexerUtil.solrRollBack(solrServer);
									log.error(e.getMessage(), e);
									throw new RuntimeException(e);
								}
								return true;
							}

							public void end() {
								log.info("Person Indexing encountered EOF @"
										+ System.currentTimeMillis());
								IndexerUtil
										.solrServerCommitShutDown(solrServer);
								RIDUtil.writeRid(RIDTYPE.PERSON, curPersonRid);
							}
						})).execute(rid);
	}

	private static void execLocation(ODatabaseDocumentTx db, final String rid,
			final SolrServer solrServer) {
		log.info(solrServer.toString());
		curLocationRid = rid;
		db.command(
				new OSQLAsynchQuery<ODocument>(QueryConstants.QRY_LOCATION,
						new OCommandResultListener() {
							int count = 0;

							public boolean result(Object record) {
								try {
									solrServer
											.addBean(getLocationDocument((ODocument) record));
									log.info("curLocationRid: "
											+ curLocationRid);
									count++;
									if (count > ApplicationConstants.INDEX_COMMIT_LIMIT) {
										RIDUtil.writeRid(RIDTYPE.LOCATION,
												curLocationRid);
										solrServer.commit();
										count = 0;
									}
								} catch (IOException | SolrServerException e) {
									IndexerUtil.solrRollBack(solrServer);
									log.error(e.getMessage(), e);
									throw new RuntimeException(e);
								}
								return true;
							}

							public void end() {
								log.info("LocationRid Indexing encountered EOF @"
										+ System.currentTimeMillis());
								IndexerUtil
										.solrServerCommitShutDown(solrServer);
								RIDUtil.writeRid(RIDTYPE.LOCATION,
										curLocationRid);
							}
						})).execute(rid);
	}

	private static void execOrganization(ODatabaseDocumentTx db,
			final String rid, final SolrServer solrServer) {
		curOrgRid = rid;
		db.command(
				new OSQLAsynchQuery<ODocument>(QueryConstants.QRY_ORGANIZATION,
						new OCommandResultListener() {
							int count = 0;

							public boolean result(Object record) {
								try {
									solrServer
											.addBean(getOrganizationDocument((ODocument) record));
									log.info("curOrgRid: " + curOrgRid);
									count++;
									if (count > ApplicationConstants.INDEX_COMMIT_LIMIT) {
										solrServer.commit();
										RIDUtil.writeRid(RIDTYPE.ORGANIZATION,
												curOrgRid);
										count = 0;
									}
								} catch (IOException | SolrServerException e) {
									IndexerUtil.solrRollBack(solrServer);
									log.error(e.getMessage(), e);
									throw new RuntimeException(e);
								}
								return true;
							}

							public void end() {
								log.info("curOrgRid Indexing encountered EOF @"
										+ System.currentTimeMillis());
								IndexerUtil
										.solrServerCommitShutDown(solrServer);
								RIDUtil.writeRid(RIDTYPE.ORGANIZATION,
										curOrgRid);
							}
						})).execute(rid);
	}

	/**
	 * <p>
	 * Convert the orient record to respective Content entity type
	 * </p>
	 * 
	 * @param doc
	 * @return
	 */
	protected static Content getContentDocument(ODocument doc) {
		Content content = new Content((String) doc.rawField(
				FieldNameConstants.FIELD_CONTENT_RID).toString(),
				(String) doc.field(FieldNameConstants.FIELD_CONTENT_ID),
				(String) doc.field(FieldNameConstants.FIELD_CONTENT_TEXT),
				(Long) doc.field(FieldNameConstants.FIELD_CONTENT_TS),
				(String) doc.field(FieldNameConstants.FIELD_CONTENT_SOURCE),
				(String) doc.field(FieldNameConstants.FIELD_CONTENT_URL));

		// Set author entity details
		if (doc.containsField(FieldNameConstants.FIELD_CONTENT_AUTHOR_RID))
			content.setauthorRid((String) doc.rawField(
					FieldNameConstants.FIELD_CONTENT_AUTHOR_RID).toString());

		if (doc.containsField(FieldNameConstants.FIELD_CONTENT_AUTHOR_NAME))
			content.setauthorName((String) doc.rawField(
					FieldNameConstants.FIELD_CONTENT_AUTHOR_NAME).toString());

		if (doc.containsField(FieldNameConstants.FIELD_CONTENT_AUTHOR_PROFILE_NAME))
			content.setauthorProfileName((String) doc.rawField(
					FieldNameConstants.FIELD_CONTENT_AUTHOR_PROFILE_NAME)
					.toString());

		if (doc.containsField(FieldNameConstants.FIELD_CONTENT_AUTHOR_PROFILE_URL))
			content.setauthorProfileUrl((String) doc.rawField(
					FieldNameConstants.FIELD_CONTENT_AUTHOR_PROFILE_URL)
					.toString());

		if (doc.containsField(FieldNameConstants.FIELD_CONTENT_AUTHOR_IMAGE_URL))
			content.setauthorImageUrl((String) doc.rawField(
					FieldNameConstants.FIELD_CONTENT_AUTHOR_IMAGE_URL)
					.toString());

		log.debug("content id: " + content.getContentRid());
		curContentRid = content.getContentRid();
		return content;
	}

	/**
	 * <p>
	 * Convert the orient record to respective Event entity type
	 * </p>
	 * 
	 * @param doc
	 * @return
	 */
	protected static Event getEventDocument(ODocument doc) {
		Event event = new Event(doc.getIdentity().toString(),
				(String) doc.field(FieldNameConstants.FIELD_EVENT_CATEGORY),
				(String) doc.field(FieldNameConstants.FIELD_EVENT_LOCATION),
				(Float) doc.field(FieldNameConstants.FIELD_EVENT_SCORE),
				(Long) doc.field(FieldNameConstants.FIELD_EVENT_TS),
				(String) doc.field(FieldNameConstants.FIELD_EVENT_ID),
				(String) doc.rawField("@rid").toString(),
				(String) doc.field(FieldNameConstants.FIELD_EVENT_NAME),
				(String) doc.field(FieldNameConstants.FIELD_EVENT_SUBCATEGORY));
		log.debug("event id: " + event.getEventId());
		curEventRid = event.getEventRid();
		return event;
	}

	/**
	 * <p>
	 * Convert the orient record to respective Person entity type
	 * </p>
	 * 
	 * @param doc
	 * @return
	 */
	protected static Person getPersonDocument(ODocument doc) {
		Person person = new Person(doc.rawField(FieldNameConstants.FIELD_RID)
				.toString(),
				(String) doc.field(FieldNameConstants.FIELD_PERSON_SOURCE),
				(String) doc.field(FieldNameConstants.FIELD_PERSON_USERID),
				(String) doc.field(FieldNameConstants.FIELD_PERSON_PROFILENM),
				(String) doc.field(FieldNameConstants.FIELD_PERSON_USERNM),
				(String) doc.field(FieldNameConstants.FIELD_PERSON_ADDRESS),
				(String) doc.field(FieldNameConstants.FIELD_PERSON_IMAGE_URL));
		log.debug("person userID:  " + person.getUserId());
		curPersonRid = person.getPersonRid();
		return person;
	}

	/**
	 * <p>
	 * Convert the orient record to respective Location entity type
	 * </p>
	 * 
	 * @param doc
	 * @return
	 */
	protected static Location getLocationDocument(ODocument doc) {
		Location location = new Location((String) doc.rawField(
				FieldNameConstants.FIELD_AT_RID).toString(),
				(String) doc.field(FieldNameConstants.FIELD_LOC_NAME));
		log.info("Location ID:  " + location.getLocationRid());
		curLocationRid = location.getLocationRid();
		return location;
	}

	/**
	 * <p>
	 * Convert the orient record to respective Location entity type
	 * </p>
	 * 
	 * @param doc
	 * @return
	 */
	protected static Organization getOrganizationDocument(ODocument doc) {
		String orgName = (String) doc.field(FieldNameConstants.FIELD_ORG_NAME);
		Organization org = new Organization((String) doc.rawField(
				FieldNameConstants.FIELD_AT_RID).toString(), orgName);
		WikiOrganizationVO orgWikiVo = WikiUtil.getWikiDetail(orgName);
		if (orgWikiVo != null) {
			org.setUrl(orgWikiVo.getUrl());
			org.setExtractedContent(orgWikiVo.getExtractedContent());
			org.setTitle(orgWikiVo.getTitle());
		}

		log.info("Organization ID:  " + org.getOrgRid());
		curOrgRid = org.getOrgRid();
		return org;
	}
}