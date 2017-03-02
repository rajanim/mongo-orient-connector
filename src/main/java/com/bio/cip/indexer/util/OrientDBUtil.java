package com.bio.cip.indexer.util;

import com.bio.cip.indexer.vo.DBCreds;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

/**
 * Utility class to provide connection open/close methods to an Orient store
 */
public class OrientDBUtil {

	private OrientDBUtil() {
	}

	public static ODatabaseDocumentTx connect(DBCreds dbCreds) {
		ODatabaseDocumentTx db = new ODatabaseDocumentTx(dbCreds.getUrl());
		db.open(dbCreds.getUser(), dbCreds.getPassword());
		return db;
	}

	public static void disconnect(ODatabaseDocumentTx db) {
		if (db != null && !db.isClosed()) {
			db.close();
		}
	}
}
