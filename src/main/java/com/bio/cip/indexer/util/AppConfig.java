package com.bio.cip.indexer.util;

import java.io.File;

import com.bio.cip.indexer.vo.DBCreds;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bio.cip.indexer.util.RIDUtil.RIDTYPE;

/**
 * Application Configuration Reader
 */
public class AppConfig {

	public static final String KEY_ORIENT_URL = "orientdb.url";
	public static final String KEY_ORIENT_USER = "orientdb.user";
	public static final String KEY_ORIENT_PASSWORD = "orientdb.password";

	public static final String KEY_OUT_PATH = "out.path";
	public static final String KEY_INDEX_SUBDIR = "index.subdir";
	public static final String KEY_RID_SUBDIR = "rid.subdir";

	public static final String INDX_PATH_CONTENT = "/content";
	public static final String INDX_PATH_EVENT = "/event";
	public static final String INDX_PATH_PERSON = "/person";

	public static Configuration config;

	private static Logger log = LoggerFactory.getLogger(AppConfig.class);

	static {
		try {
			config = new PropertiesConfiguration("app.properties");
		} catch (ConfigurationException cex) {
			log.error("Failed to load configuration ", cex);
			throw new ExceptionInInitializerError(cex);
		}
	}

	private AppConfig() {
	}

	public static String get(final String key) {
		return config.getString(key);
	}

	public static DBCreds getDBCreds() {
		return new DBCreds(get(KEY_ORIENT_URL), get(KEY_ORIENT_USER),
				get(KEY_ORIENT_PASSWORD));
	}

	private static String getOutPath() {
		return get(KEY_OUT_PATH);
	}

	private static String getOutSubDirPath(final String outSubDir) {
		return getOutPath() + File.separatorChar + get(outSubDir);
	}

	public static String getIndexPath() {
		return getOutSubDirPath(KEY_INDEX_SUBDIR);
	}

	public static String getIndexPath(RIDUtil.RIDTYPE ridType) {
		return getIndexPath()
				+ ((ridType == RIDTYPE.CONTENT) ? INDX_PATH_CONTENT
						: ((ridType == RIDTYPE.EVENT) ? INDX_PATH_EVENT
								: INDX_PATH_PERSON));
	}

	public static String getRidPath() {
		return getOutSubDirPath(KEY_RID_SUBDIR);
	}
}