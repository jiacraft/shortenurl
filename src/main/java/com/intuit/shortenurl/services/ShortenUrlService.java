package com.intuit.shortenurl.services;

import com.intuit.shortenurl.exception.UrlShortenServiceException;

public interface ShortenUrlService {
	/**
	 * 
	 * @param longURL
	 * @return
	 */
	public String shortenUrl(String longUrl) throws UrlShortenServiceException;
	
	public String retrieveUrl(String shortUrl) throws UrlShortenServiceException;
}
