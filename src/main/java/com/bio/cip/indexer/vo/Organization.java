package com.bio.cip.indexer.vo;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class Organization implements Serializable {

	private static final long serialVersionUID = -4783068200761684207L;

	@Field("entityType")
	private String entityType = Organization.class.getSimpleName();

	@Field("Id")
	private String orgRid;

	@Field("orgName")
	private String orgName;

	@Field("url")
	private String url;

	@Field("extractedContent")
	private String extractedContent;

	@Field("title")
	private String title;

	public Organization() {

	}

	public Organization(String rid, String orgName) {
		this.orgRid = rid;
		this.orgName = orgName;
	}

	public String getOrgRid() {
		return orgRid;
	}

	@Override
	public String toString() {
		return "Location [orgRid=" + orgRid + ", orgName=" + orgName + "]";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExtractedContent() {
		return extractedContent;
	}

	public void setExtractedContent(String extractedContent) {
		this.extractedContent = extractedContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}