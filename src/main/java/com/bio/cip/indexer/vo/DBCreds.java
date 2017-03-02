package com.bio.cip.indexer.vo;

import java.io.Serializable;

/**
 * POJO for db connectivity details
 */
public class DBCreds implements Serializable {

	private static final long serialVersionUID = -5205314256575196288L;

	private String url;
	private String user;
	private String password;

	public DBCreds(String url, String user, String password) {
		super();
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}