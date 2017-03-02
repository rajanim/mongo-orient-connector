package com.bio.cip.indexer.vo;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Index structure for Content Entity
 */
public class Content implements Serializable {

	private static final long serialVersionUID = -8665940038370463140L;

	@Field("entityType")
	private String entityType = Content.class.getSimpleName();
	@Field("contentId")
	private String contentId;
	@Field("contentText")
	private String text;
	@Field("contentTS")
	private Long timestamp;

	@Field("Id")
	private String contentRid;

	@Field("source")
	private String source;
	
	@Field("contentUrl")
	private String contentUrl;

	@Field("authorName")
	private String authorName;

	@Field("authorRid")
	private String authorRid;

	@Field("authorProfileUrl")
	private String authorProfileUrl;

	@Field("authorImageUrl")
	private String authorImageUrl;

	@Field("authorProfileName")
	private String authorProfileName;

	public Content() {
	}

	public Content(String rid, String contentId, String text, Long timestamp, String source, String contentUrl) {
		this.contentRid = rid;
		this.contentId = contentId;
		this.text = text;
		this.timestamp = timestamp;
		this.source = source;
		this.contentUrl = contentUrl;
	}

	public Content(String rid, String id, String text, Long timestamp,
			String authorName, String authorRid, String source,
			String authorProfileUrl, String authorImageUrl, String contentUrl) {
		this(rid, id, text, timestamp, source, contentUrl);
		this.authorName = authorName;
		this.authorRid = authorRid;
		this.source = source;
		this.authorProfileUrl = authorProfileUrl;
		this.authorImageUrl = authorImageUrl;
		this.contentUrl = contentUrl;
	}

	public void setcontentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setcontentRid(String contentRid) {
		this.contentRid = contentRid;
	}

	public String getContentRid() {
		return contentRid;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	/* Author Entites, getter and setters */
	public void setauthorRid(String authorRid) {
		this.authorRid = authorRid;
	}

	public String getAuthorRid() {
		return authorRid;
	}

	public void setauthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getauthorName() {
		return authorName;
	}

	public void setauthorProfileUrl(String authorProfileUrl) {
		this.authorProfileUrl = authorProfileUrl;
	}

	public String getauthorProfileUrl() {
		return authorProfileUrl;
	}

	public void setauthorImageUrl(String authorImageUrl) {
		this.authorImageUrl = authorImageUrl;
	}

	public String getauthorImageUrl() {
		return authorImageUrl;
	}

	public void setauthorProfileName(String authorProfileName) {
		this.authorProfileName = authorProfileName;
	}

	public String getauthorProfileName() {
		return authorProfileName;
	}

	@Override
	public String toString() {
		return "Content [id=" + contentRid + ", text=" + text + ", timestamp="
				+ timestamp + ", author=" + authorName + ", contentRid()="
				+ contentRid + "]";
	}
}