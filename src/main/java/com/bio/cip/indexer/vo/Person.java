package com.bio.cip.indexer.vo;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class Person implements Serializable {

	private static final long serialVersionUID = -4783068200761684207L;

	@Field("entityType")
	private String entityType = Person.class.getSimpleName();
	@Field("source")
	private String source;
	@Field("Id")
	private String userId;
	@Field("profileName")
	private String profileName;
	@Field("userName")
	private String userName;
	@Field("address")
	private String address;
	@Field("personRid")
	private String personRid;
	@Field("profileImageUrl")
	private String personImageUrl;

	public Person() {

	}

	public Person(String rid, String source, String userId, String profileName,
			String userName, String address, String personImageUrl) {
		this.personRid = rid;
		this.source = source;
		this.userId = userId;
		this.profileName = profileName;
		this.userName = userName;
		this.address = address;
		this.personImageUrl = personImageUrl;
	}

	public String getSource() {
		return source;
	}

	public String getUserId() {
		return userId;
	}

	public String getProfileName() {
		return profileName;
	}

	public String getUserName() {
		return userName;
	}

	public String getAddress() {
		return address;
	}

	public String getPersonRid() {
		return personRid;
	}

	@Override
	public String toString() {
		return "Person [source=" + source + ", userId=" + userId
				+ ", profileName=" + profileName + ", userName=" + userName
				+ ", address=" + address + ", personRid()=" + personRid + "]";
	}
}