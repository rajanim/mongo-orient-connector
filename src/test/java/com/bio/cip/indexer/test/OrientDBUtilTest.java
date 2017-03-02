package com.bio.cip.indexer.test;

import com.bio.cip.indexer.util.OrientDBUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bio.cip.indexer.util.AppConfig;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

public class OrientDBUtilTest {

	private ODatabaseDocumentTx db;

	@Before
	public void before() {
		db = OrientDBUtil.connect(AppConfig.getDBCreds());
	}

	@After
	public void after() {
		OrientDBUtil.disconnect(db);
	}

	@Test
	public void testConnected() {
		Assert.assertFalse(db.isClosed());
	}
}
