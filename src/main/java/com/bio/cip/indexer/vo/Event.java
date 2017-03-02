package com.bio.cip.indexer.vo;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class Event implements Serializable {

	private static final long serialVersionUID = 1733931785406242854L;

	@Field("entityType")
	private String entityType = Event.class.getSimpleName();
	@Field("category")
	private String category;
	@Field("location")
	private String location;
	@Field("score")
	private Float score;
	@Field("eventTS")
	private Long timestamp;
	@Field("Id")
	private String eventId;
	@Field("eventRid")
	private String eventRid;
	@Field("eventName")
	private String eventName;
	@Field("subCategory")
	private String subCategory;

	public Event() {
	}

	public Event(String rid, String category, String location, Float score,
			Long timestamp, String eventId, String eventRid, String eventName,
			String subCategory) {
		this.eventRid = eventRid;
		this.category = category;
		this.location = location;
		this.score = score;
		this.timestamp = timestamp;
		this.eventId = eventId;
		this.eventName = eventName;
		this.subCategory = subCategory;
	}

	public String getCategory() {
		return category;
	}

	public String getLocation() {
		return location;
	}

	public Float getScore() {
		return score;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "Event [category=" + category + ", location=" + location
				+ ", score=" + score + ", timestamp=" + timestamp
				+ ", getRid()=" + eventRid + "]";
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventRid() {
		return eventRid;
	}
}