package com.bio.cip.indexer.vo;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class Location implements Serializable {

	private static final long serialVersionUID = -4783068200761684207L;

	@Field("entityType")
	private String entityType = Location.class.getSimpleName();

	@Field("Id")
	private String locationRid;

	@Field("name")
	private String name;

	public Location() {

	}

	public Location(String rid, String name) {
		this.locationRid = rid;
		this.name = name;
	}

	public String getLocationRid() {
		return locationRid;
	}

	@Override
	public String toString() {
		return "Location [locationRid=" + locationRid + ", name=" + name + "]";
	}

}