package com.bio.cip.indexer.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.bio.cip.indexer.util.RIDUtil;
import com.bio.cip.indexer.util.RIDUtil.RIDTYPE;

@Ignore
public class RIDUtilTest {
	private static final String CONTENT_RID = "xyz";

	@Test
	public void testWriteRID() throws Exception {
		RIDUtil.writeRid(RIDTYPE.CONTENT, CONTENT_RID);
	}

	@Test
	public void testReadRID() throws Exception {
		Assert.assertEquals(RIDUtil.readRid(RIDTYPE.CONTENT), CONTENT_RID);
	}

}
