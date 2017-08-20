package com.intuit.shortenurl.services.impl;

import org.junit.Before;
import org.junit.Test;

import com.intuit.shortenurl.exception.ErrorCode;
import com.intuit.shortenurl.exception.UrlShortenServiceException;

import static org.junit.Assert.*;

public class ServiceExceptionTest {
	private UrlShortenServiceException exception;
	
	@Before
    public void setUp() {
		exception = new UrlShortenServiceException(ErrorCode.INTERNAL_ERROR, "Exception message");
	}
	
	@Test
    public void testUrlShortenServiceException() throws Exception {
		assertEquals(ErrorCode.INTERNAL_ERROR, exception.getCode());
		assertEquals("Exception message", exception.getMsg());
	}
}
