package com.bio.cip.indexer.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bio.cip.constants.ApplicationConstants;

public class IndexerUtil {

	private static Logger log = LoggerFactory.getLogger(IndexerUtil.class);
	private static final String GET_NOT_QUERY = "!";

	public static void solrRollBack(final SolrServer solrServer) {
		try {
			solrServer.rollback();
		} catch (SolrServerException | IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public static void solrServerCommitShutDown(final SolrServer solrServer) {
		try {
			solrServer.commit();
		} catch (SolrServerException | IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			solrServer.shutdown();
		}
	}

	/**
	 * <p>
	 * To read the keyword list file, build the solr search query for keywords
	 * and return the query string
	 * </p>
	 * 
	 * @return KeyWordsQueryString
	 */
	public static String getKeyWordsQueryString(String defaultFieldName) {
		StringBuffer sbRetString = new StringBuffer();
		try {
			Path p = Paths.get(AppConfig
					.get(ApplicationConstants.KEYWORDS_LIST_FILE_PATH));
			List<String> lines = Files
					.readAllLines(p, Charset.defaultCharset());
			if (!lines.isEmpty()) {
				sbRetString.append(defaultFieldName)
						.append(":(");
				for (String line : lines)
					sbRetString.append("\"").append(line.trim()).append("\"")
							.append(" ");
				sbRetString.append(")");
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		log.debug("keyword query string: " + sbRetString.toString());
		return sbRetString.toString();
	}

	/**
	 * <p>
	 * solr roll back method
	 * </p>
	 * 
	 * @param e
	 */
	public static void rollBackSolrDelete(Exception e, SolrServer solrServer) {
		try {
			solrServer.rollback();
		} catch (SolrServerException | IOException e1) {
			log.error(e1.getMessage());
			throw new RuntimeException(e1.getMessage());
		}
	}
	
	/**
	 * <p>
	 * To read the keyword list file, build the solr search query and return the
	 * query string
	 * </p>
	 * 
	 * @return KeyWordsQueryString
	 */
	public static String getNotMatchingKeyWordsQuery(String defaultFieldName) {
		StringBuffer sbRetString = new StringBuffer();
		try {
			Path p = Paths.get(AppConfig
					.get(ApplicationConstants.KEYWORDS_LIST_FILE_PATH));
			List<String> lines = Files
					.readAllLines(p, Charset.defaultCharset());
			if (!lines.isEmpty()) {
				sbRetString
						.append(GET_NOT_QUERY
								+ defaultFieldName)
						.append(":(");
				for (String line : lines)
					sbRetString.append("\"").append(line.trim()).append("\"")
							.append(" ");
				sbRetString.append(")");
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		log.debug("keyword query string: " + sbRetString.toString());
		return sbRetString.toString();
	}

}
