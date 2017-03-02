/**
 * 
 */
package com.bio.cip.feed.model;

import java.io.Serializable;

/**
 * @author Piyush.Sharma
 * 
 */
public class UrlEnties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6556931287535334957L;

	private String url;
	private String longUrl;

	public UrlEnties(String url) {
		this.url = url;
	}

	public UrlEnties() {
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the longUrl
	 */
	public String getLongUrl() {
		return longUrl;
	}

	/**
	 * @param longUrl
	 *            the longUrl to set
	 */
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

}
