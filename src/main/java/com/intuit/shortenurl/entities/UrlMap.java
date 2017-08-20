package com.intuit.shortenurl.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UrlMap implements Serializable {

	/**
	 * 	generated
	 */
	private static final long serialVersionUID = 3274385866213647339L;
	
	private @Id @GeneratedValue Long id;
	
	private String originalUrl;
	
	private String key;
	
	public UrlMap () {;}
	
	/*
	public UrlMap (String originalUrl, String key) {
		this.setOriginalUrl(originalUrl);
		this.setKey(key);
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	} */

	
}
