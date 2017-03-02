package com.bio.cip.entities;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class Feed implements Serializable {

	/**
	 * Feed entity
	 */
	private static final long serialVersionUID = -2044895207653632483L;

	@Field("id")
	private String id;

	@Field("feedSource")
	private String feedSource;

	@Field("pubTimeStamp")
	private Long pubTimeStamp;

	@Field("feedDesc")
	private String feedDesc;

	@Field("feedTitle")
	private String feedTitle;

	@Field("feedUrl")
	private String feedUrl;

	@Field("feedContent")
	private String feedContent;
	
	@Field("contentSource")
	private String contentSource;
	
	@Field("contentId")
	private String contentId;
	
	@Field("feedUser")
	private String feedUser;

	@Field("originalContent")
	private String originalContent;
	
	public Feed() {

	}

	public Feed(String id, String feedUrl, String feedContent) {
		this.id = id;
		this.feedContent = feedContent;
		this.feedUrl = feedUrl;
	}

	public String getFeedContent() {
		return feedContent;
	}

	public void setFeedContent(String feedContent) {
		this.feedContent = feedContent;
	}

	public String getFeedUrl() {
		return feedUrl;
	}

	public void setFeedUrl(String feedUrl) {
		this.feedUrl = feedUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFeedSource() {
		return feedSource;
	}

	public void setFeedSource(String feedSource) {
		this.feedSource = feedSource;
	}

	public Long getPublishTimeStamp() {
		return pubTimeStamp;
	}

	public void setPublishTimeStamp(Long publishTimeStamp) {
		this.pubTimeStamp = publishTimeStamp;
	}

	public String getFeedDesc() {
		return feedDesc;
	}

	public void setFeedDesc(String feedDesc) {
		this.feedDesc = feedDesc;
	}

	public String getFeedTitle() {
		return feedTitle;
	}

	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}

	public String toString() {
		return "feedSource :  " + feedSource + " feedContent :" + feedContent
				+ " feedUrl :  " + feedUrl + " feedDesc :  " + feedDesc
				+ " feedTitle :  " + feedTitle + " pubTimeStamp : " + pubTimeStamp;
	}

	public Long getPubTimeStamp() {
		return pubTimeStamp;
	}

	public void setPubTimeStamp(Long pubTimeStamp) {
		this.pubTimeStamp = pubTimeStamp;
	}

	public String getContentSource() {
		return contentSource;
	}

	public void setContentSource(String contentSource) {
		this.contentSource = contentSource;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getFeedUser() {
		return feedUser;
	}

	public void setFeedUser(String feedUser) {
		this.feedUser = feedUser;
	}

	public String getOriginalContent() {
		return originalContent;
	}

	public void setOriginalContent(String originalContent) {
		this.originalContent = originalContent;
	}
}
