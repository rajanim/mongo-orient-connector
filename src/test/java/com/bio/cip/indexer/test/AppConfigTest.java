package com.bio.cip.indexer.test;

import org.junit.Assert;
import org.junit.Test;

import com.bio.cip.indexer.util.AppConfig;

public class AppConfigTest {

	@Test
	public void testConfig() {
		Assert.assertNotNull(AppConfig.getDBCreds());
	}
}
