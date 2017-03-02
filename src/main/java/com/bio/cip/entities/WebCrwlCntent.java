package com.bio.cip.entities;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class WebCrwlCntent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3512441392138846363L;

	@Field("id")
	private String id;

	@Field("title")
	private String title;
	
	@Field("content")
	private String content;
	
	@Field("url")
	private String url;
	
	private String source = "WebContent";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String toString() {
		return "url : " + url + "title : " + title + "content : " + content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
