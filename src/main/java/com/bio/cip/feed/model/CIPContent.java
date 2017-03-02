/**
 * 
 */
package com.bio.cip.feed.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Piyush.Sharma
 * 
 */
public class CIPContent {

	private String id = null;
	private String parentContentId = null;
	private Boolean isRetweet = null;
	private Boolean isReply = null;
	private String contentType = null;
	private String contentUrl = null;
	private String tittle = null;
	private String content = null;
	private String originalContent = null;
	private String extractedText = null;
	private String language = null;
	private Integer favouritesCount = null;
	private Integer sharedCount = null;
	private Integer likesCount = null;
	private Integer retweetCount = null;
	private List<String> hashtaggedEntries = null;
	private List<CIPUser> taggedUsers = null;
	private List<String> retweetedBy = null;
	private List<UrlEnties> urlsSharedInContent = null;
	private Long time = null;
	private String feedSource=null;

	public String getFeedSource() {
		return feedSource;
	}

	public void setFeedSource(String feedSource) {
		this.feedSource = feedSource;
	}

	/**
	 * @return the hashtaggedEntries
	 */
	public List<String> getHashtaggedEntries() {
		return hashtaggedEntries;
	}

	/**
	 * @param hashtaggedEntries
	 *            the hashtaggedEntries to set
	 */
	public void setHashtaggedEntries(List<String> hashtaggedEntries) {
		this.hashtaggedEntries = hashtaggedEntries;
	}

	/**
	 * @return the taggedUsers
	 */
	public List<CIPUser> getTaggedUsers() {
		return taggedUsers;
	}

	/**
	 * @param taggedUsers
	 *            the taggedUsers to set
	 */
	public void setTaggedUsers(List<CIPUser> taggedUsers) {
		this.taggedUsers = taggedUsers;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public List<UrlEnties> getUrlsSharedInContent() {
		return urlsSharedInContent;
	}

	public void setUrlsSharedInContent(List<UrlEnties> urlsSharedInContent) {
		this.urlsSharedInContent = urlsSharedInContent;
	}

	/**
	 * @return the extractedText
	 */
	public String getExtractedText() {
		return extractedText;
	}

	/**
	 * @param extractedText
	 *            the extractedText to set
	 */
	public void setExtractedText(String extractedText) {
		this.extractedText = extractedText;
	}

	/**
	 * @param extractedText
	 *            the extractedText to set
	 */
	public void setExtractedText(List<String> extractedText) {
		StringBuffer buffer = new StringBuffer();
		for (String text : extractedText) {
			buffer.append(" ").append(text);
		}
		this.extractedText = buffer.toString();
	}

	/**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}

	/**
	 * @return the favouritesCount
	 */
	public Integer getFavouritesCount() {
		return favouritesCount;
	}

	/**
	 * @param favouritesCount
	 *            the favouritesCount to set
	 */
	public void setFavouritesCount(Integer favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public Integer getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;
	}

	public Integer getSharedCount() {
		return sharedCount;
	}

	public void setSharedCount(Integer sharedCount) {
		this.sharedCount = sharedCount;
	}

	public Integer getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Integer likesCount) {
		this.likesCount = likesCount;
	}

	/**
	 * @return the retweetedBy
	 */
	public List<String> getRetweetedBy() {
		return retweetedBy;
	}

	/**
	 * @param retweetedBy
	 *            the retweetedBy to set
	 */
	public void setRetweetedBy(List<String> retweetedBy) {
		this.retweetedBy = retweetedBy;
	}

	public void addUrlsSharedInContent(String source) {
		if (null == urlsSharedInContent) {
			urlsSharedInContent = new LinkedList<UrlEnties>();
		}
		urlsSharedInContent.add(new UrlEnties(source));
	}

	/**
	 * @return the isRetweet
	 */
	public boolean isRetweet() {
		return isRetweet;
	}

	/**
	 * @param isRetweet
	 *            the isRetweet to set
	 */
	public void setRetweet(boolean isRetweet) {
		this.isRetweet = isRetweet;
	}

	/**
	 * @return the originalContent
	 */
	public String getOriginalContent() {
		return originalContent;
	}

	/**
	 * @param originalContent
	 *            the originalContent to set
	 */
	public void setOriginalContent(String originalContent) {
		this.originalContent = originalContent;
	}

	/**
	 * @return the isRetweet
	 */
	public Boolean getIsRetweet() {
		return isRetweet;
	}

	/**
	 * @param isRetweet
	 *            the isRetweet to set
	 */
	public void setIsRetweet(Boolean isRetweet) {
		this.isRetweet = isRetweet;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the parentContentId
	 */
	public String getParentContentId() {
		return parentContentId;
	}

	/**
	 * @param parentContentId
	 *            the parentContentId to set
	 */
	public void setParentContentId(String parentContentId) {
		this.parentContentId = parentContentId;
	}

	/**
	 * @return the isReplied
	 */
	public Boolean getIsReply() {
		return isReply;
	}

	/**
	 * @param isReplied
	 *            the isReplied to set
	 */
	public void setIsReply(Boolean isReplied) {
		isReply = isReplied;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CIPContent [id=" + id + ", parentContentId=" + parentContentId + ", isRetweet=" + isRetweet
				+ ", isReply=" + isReply + ", contentType=" + contentType + ", contentUrl=" + contentUrl + ", tittle="
				+ tittle + ", content=" + content + ", originalContent=" + originalContent + ", extractedText="
				+ extractedText + ", language=" + language + ", favouritesCount=" + favouritesCount + ", sharedCount="
				+ sharedCount + ", likesCount=" + likesCount + ", retweetCount=" + retweetCount
				+ ", hashtaggedEntries=" + hashtaggedEntries + ", taggedUsers=" + taggedUsers + ", retweetedBy="
				+ retweetedBy + ", urlsSharedInContent=" + urlsSharedInContent + ", time=" + time + "]";
	}

}
