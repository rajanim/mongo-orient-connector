/**
 * 
 */
package com.bio.cip.feed.model;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * @author Piyush.Sharma
 * 
 */
public class CIPPayload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 913447175474257648L;

	private String source = null;
	private CIPLocation geoLocation = null;
	private CIPUser user = null;
	private CIPContent content = null;
	private long systemTime;

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the geoLocation
	 */
	public CIPLocation getGeoLocation() {
		return geoLocation;
	}

	/**
	 * @param geoLocation
	 *            the geoLocation to set
	 */
	public void setGeoLocation(CIPLocation geoLocation) {
		this.geoLocation = geoLocation;
	}

	/**
	 * @return the user
	 */
	public CIPUser getUserInfo() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUserInfo(CIPUser userInfo) {
		user = userInfo;
	}

	/**
	 * @return the content
	 */
	public CIPContent getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(CIPContent content) {
		this.content = content;
	}

	/**
	 * @return the systemTime
	 */
	public long getSystemTime() {
		return systemTime;
	}

	/**
	 * @param systemTime
	 *            the systemTime to set
	 */
	public void setSystemTime(long systemTime) {
		this.systemTime = systemTime;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
