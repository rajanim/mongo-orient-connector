package com.bio.cip.indexer.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Utility class to keep track of the last record id (RID) processed from the
 * db by maintaining the RIDs on persistent .rid files categorized by type
 */
public class RIDUtil {

	private static final String CONTENT_LASTINDEXED_RID = "content.rid";
	private static final String EVENT_LASTINDEXED_RID = "event.rid";
	private static final String PERSON_LASTINDEXED_RID = "person.rid";
	private static final String LOCATION_LASTINDEXED_RID = "location.rid";
	private static final String ORGANIZATION_LASTINDEXED_RID = "organization.rid";

	private static Logger log = LoggerFactory.getLogger(RIDUtil.class);

	private RIDUtil() {
	}

	public enum RIDTYPE {
		CONTENT(CONTENT_LASTINDEXED_RID), EVENT(EVENT_LASTINDEXED_RID), PERSON(
				PERSON_LASTINDEXED_RID), LOCATION(LOCATION_LASTINDEXED_RID), ORGANIZATION(ORGANIZATION_LASTINDEXED_RID);

		String fileNm;

		RIDTYPE(String fileNm) {
			this.fileNm = fileNm;
		}

		String getFileNm() {
			return fileNm;
		}
	}

	public static void writeRid(RIDTYPE rid, String value) {
		try {
			File file = getRidFile(rid);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
			FileUtils.writeStringToFile(file, value, false);
		} catch (IOException ioex) {
			log.error("Caught exception trying to write rid ", ioex);
			throw new RuntimeException("Caught exception trying to write rid ",
					ioex);
		}
	}

	private static File getRidFile(RIDTYPE rid) {
		return new File(AppConfig.getRidPath() + File.separatorChar
				+ rid.getFileNm());
	}

	public static String readRid(RIDTYPE rid) {
		try {
			File file = getRidFile(rid);

			if (!file.exists()) {
				return null;
			}
			List<String> contents = FileUtils.readLines(file);
			return (contents.size() == 0) ? null : contents.get(0);

		} catch (IOException ioex) {
			log.error("Caught exception trying to read rid ", ioex);
			throw new RuntimeException("Caught exception trying to read rid ",
					ioex);
		}
	}
}