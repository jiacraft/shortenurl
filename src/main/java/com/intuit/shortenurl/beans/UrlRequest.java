package com.intuit.shortenurl.beans;

import java.io.Serializable;

public class UrlRequest implements Serializable {

	/**
	 * auto-generated
	 */
	private static final long serialVersionUID = 6527973549571115065L;
	
	private String longUrl;

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	
}
