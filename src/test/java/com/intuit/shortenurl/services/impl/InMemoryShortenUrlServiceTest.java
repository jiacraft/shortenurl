package com.intuit.shortenurl.services.impl;

import org.junit.Before;
import org.junit.Test;

import com.intuit.shortenurl.exception.UrlShortenServiceException;

import static org.junit.Assert.*;

public class InMemoryShortenUrlServiceTest {
	private InMemoryShortenUrlService svc;
	
	@Before
    public void setUp() {
		svc = new InMemoryShortenUrlService();
	}
	
	@Test
    public void testShortenUrlSuccess() throws Exception {
		String oriUrl = "http://mysuccess.shortuel.com/aaa";
		String shortUrl = svc.shortenUrl(oriUrl);
		
		assertNotNull(shortUrl);
	}
	
	@Test(expected = UrlShortenServiceException.class)
    public void testShortenUrlInvalidUrl() throws Exception {
		String oriUrl = "mysuccess";
		svc.shortenUrl(oriUrl);
	}
	
	@Test
	public void testShortenUrlSuccess2() throws Exception {
		InMemoryShortenUrlService.urlKeyMap.put("originalUrl.com", "urlKey");
		
		String shortUrl = svc.shortenUrl("originalUrl.com");
		
		assertEquals(InMemoryShortenUrlService.BASE_DOMAIN + "/urlKey", shortUrl);
	}
	
	@Test
	public void testRetrieveUrl() throws Exception {
		InMemoryShortenUrlService.keyUrlMap.put("urlKey", "originalUrl.com");
		String originalUrl = svc.retrieveUrl(InMemoryShortenUrlService.BASE_DOMAIN + "/urlKey");
		
		assertEquals("originalUrl.com", originalUrl);
	}
	
	@Test(expected = UrlShortenServiceException.class)
	public void testRetrieveUrlNotExisted () throws Exception {
		svc.retrieveUrl(InMemoryShortenUrlService.BASE_DOMAIN + "/urlKey");
	}
	
	@Test(expected = UrlShortenServiceException.class)
	public void testRetrieveUrlInvalid () throws Exception {
		svc.retrieveUrl("/urlKey");
	}
}
