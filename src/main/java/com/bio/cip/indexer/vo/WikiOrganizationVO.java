package com.bio.cip.indexer.vo;

import java.io.Serializable;

public class WikiOrganizationVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5096339297044362873L;

	private String url;

	private String extractedContent;

	private String title;

	public WikiOrganizationVO() {
	}

	public WikiOrganizationVO(String url, String content, String title) {
		this.url = url;
		this.title = title;
		this.extractedContent = content;
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
