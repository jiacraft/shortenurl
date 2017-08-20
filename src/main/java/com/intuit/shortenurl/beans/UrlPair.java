package com.intuit.shortenurl.beans;

import java.io.Serializable;

public class UrlPair implements Serializable {

	/**
	 * auto-generated
	 */
	private static final long serialVersionUID = 6527973549571115065L;
	
	private String shortUrl;
	
	private String longUrl;
	
	public UrlPair(String sUrl, String lUrl) {
		this.shortUrl = sUrl;
		this.longUrl = lUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	
}
