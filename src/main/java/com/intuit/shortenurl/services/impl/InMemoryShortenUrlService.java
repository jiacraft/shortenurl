package com.intuit.shortenurl.services.impl;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;

import com.intuit.shortenurl.exception.ErrorCode;
import com.intuit.shortenurl.exception.UrlShortenServiceException;
import com.intuit.shortenurl.services.ShortenUrlService;

public class InMemoryShortenUrlService implements  ShortenUrlService{
	
	private static final Logger logger = Logger.getLogger(InMemoryShortenUrlService.class);
	// private static final String BASE_URL_PATTERN = "[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
	private static final String BASE_URL_PATTERN = "[a-zA-Z0-9_\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
	
	public static final String BASE_DOMAIN = "http://intu.su";
	private static final int KEY_LENGTH = 7;
	
	public static final char ALL_CHARS[];
	
	public static Map <String, String> keyUrlMap;
	public static Map <String, String> urlKeyMap;
	private Random myRandom;
	
	static {
		ALL_CHARS = new char[62];
		for (int i = 0; i < 62; i++) {
			int j = 0;
			if (i < 10) {
				j = i + 48;
			} else if (i > 9 && i <= 35) {
				j = i + 55;
			} else {
				j = i + 61;
			}
			ALL_CHARS[i] = (char) j;
		}
		
		keyUrlMap = new ConcurrentHashMap<String, String>();
		urlKeyMap = new ConcurrentHashMap<String, String>();
	}
	
	public InMemoryShortenUrlService () {		
		myRandom = new Random();
	}

	@Override
	@Cacheable("shortenUrl")
	public String shortenUrl(String originalUrl) throws UrlShortenServiceException {	
		
		logger.debug("To get shorten URL from service, long URL: " + originalUrl);
		
		String shortUrl = "";
		if (validateUrl(originalUrl)) {
			// String longUrl = extractUrl(originalUrl);
			if (urlKeyMap.containsKey(originalUrl)) {
				shortUrl = BASE_DOMAIN + "/" + urlKeyMap.get(originalUrl);
			} else {
				String key = generateUrlKey();
				shortUrl = BASE_DOMAIN + "/" + key;
				
				keyUrlMap.put(key, originalUrl);
				urlKeyMap.put(originalUrl, key);
			}
		} else {
			throw new UrlShortenServiceException(ErrorCode.INVALUD_URL_FORMAT);
		}

		return shortUrl;
	}

	@Override
	@Cacheable("originalUrl")
	public String retrieveUrl(String shortUrl) throws UrlShortenServiceException {
		
		logger.debug("To get original URL from service, short URL: " + shortUrl);
		
		String originalUrl = "";
		
		if(shortUrl.length() > BASE_DOMAIN.length()) {
			String key = shortUrl.substring(BASE_DOMAIN.length() + 1);
			originalUrl = keyUrlMap.get(key);
			if(StringUtils.isEmpty(originalUrl)) {
				throw new UrlShortenServiceException(ErrorCode.NOT_FOUND);
			}
		} else {
			throw new UrlShortenServiceException(ErrorCode.INVALUD_URL_FORMAT, "the value of shorten URL is not valid");
		}
		return originalUrl;
	}
	
	/*
	private String getUrlKey(String longUrl) {
		String key;
		key = generateUrlKey();
		keyUrlMap.put(key, longUrl);
		urlKeyMap.put(longUrl, key);
		return key;
	} */
	
	private String generateUrlKey() {
		String key = "";
		boolean flag = true;
		while (flag) {
			key = "";
			for (int i = 0; i < KEY_LENGTH; i++) {
				key += ALL_CHARS[myRandom.nextInt(62)];
			}
			
			if (!keyUrlMap.containsKey(key)) {
				flag = false;
			}
		}
		return key;
	}
 	
	/*
	private String extractUrl(String url) {
		if (url.substring(0, 7).equals("http://"))
			url = url.substring(7);

		if (url.substring(0, 8).equals("https://"))
			url = url.substring(8);

		//if (url.charAt(url.length() - 1) == '/')
		//	url = url.substring(0, url.length() - 1);
		return url;
	} */

	// 
	public boolean validateUrl(String url) {
		String urlPattern = null;
		
		if(url.startsWith("http://") || url.startsWith("https://")) {
			urlPattern = "^http(s{0,1})://" + BASE_URL_PATTERN;
		} else {
			urlPattern = BASE_URL_PATTERN;
		}
		
		return url.matches(urlPattern);
	}
}
