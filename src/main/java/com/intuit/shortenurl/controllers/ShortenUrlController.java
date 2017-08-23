package com.intuit.shortenurl.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.shortenurl.beans.UrlPair;
import com.intuit.shortenurl.beans.UrlRequest;
import com.intuit.shortenurl.exception.ErrorCode;
import com.intuit.shortenurl.exception.ServiceError;
import com.intuit.shortenurl.exception.UrlShortenServiceException;
import com.intuit.shortenurl.services.ShortenUrlService;

/**
 * @author bruce.jia
 * @Date Aug. 19, 2017
 * @version 
 */
@RestController
public class ShortenUrlController {
	
	private static final Logger logger = Logger.getLogger(ShortenUrlController.class);
	
	@Autowired
	ShortenUrlService svc;
	
	@RequestMapping(path="/url", method = RequestMethod.POST,
			consumes="application/json", produces={"application/json"}) 
	@ResponseBody
	public UrlPair shortUrl(@RequestBody UrlRequest req) throws UrlShortenServiceException {
		if(req == null || StringUtils.isEmpty(req.getLongUrl() )) {
			throw new UrlShortenServiceException(
					ErrorCode.NULL_EMPTY_URL);
		}
		
		logger.debug("Request for shorten URL, ori url: " + req.getLongUrl());
		
		String shortUrl = svc.shortenUrl(req.getLongUrl());		
		return new UrlPair(shortUrl, req.getLongUrl());
	}
	
	@RequestMapping(path="/url", method = RequestMethod.GET, produces={"application/json"})
	@ResponseBody
	public UrlPair queryUrl(HttpServletRequest request) throws UrlShortenServiceException {
		if(request.getParameterMap().isEmpty()) {
			throw new UrlShortenServiceException(ErrorCode.NULL_EMPTY_URL, "Http GET require 'shortUrl' Parameter");
		}
		
		if(StringUtils.isEmpty(request.getParameter("shortUrl"))) {
			throw new UrlShortenServiceException(ErrorCode.INVALID_REQUEST, "Http GET require 'shortUrl' Parameter");
		}
		
		String shortUrl = request.getParameter("shortUrl");
		
		logger.debug("Request to get original URL, short URL: " + shortUrl);
		
		String longUrl = svc.retrieveUrl(shortUrl);
		return new UrlPair(shortUrl, longUrl);
	}
	
	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<ServiceError> errorHandler(UrlShortenServiceException se){  
		
		ServiceError error = new ServiceError();
		HttpStatus status = null;
		if (se.getCode() == ErrorCode.INVALUD_URL_FORMAT) {	
			//error.setCode(ErrorCode.INVALUD_URL_FORMAT);
			error.setMessage("URL in request is not valid");
			error.setReason("InvalidURL");
			status = HttpStatus.BAD_REQUEST;
		} else if (se.getCode() == ErrorCode.INVALID_REQUEST) {
			//error.setCode(ErrorCode.INVALID_REQUEST);
			error.setMessage(se.getMsg() == null ? "invalid request" : se.getMsg());
			error.setReason("InvalidRequest");
			status = HttpStatus.BAD_REQUEST;
		} else if (se.getCode() == ErrorCode.NULL_EMPTY_URL) {
			//error.setCode(ErrorCode.NULL_EMPTY_URL);
			error.setMessage(se.getMsg() == null ? "null or empty URL in request" : se.getMsg());
			error.setReason("EmptyURL");
			status = HttpStatus.BAD_REQUEST;
		} else if (se.getCode() == ErrorCode.NOT_FOUND) {
			//error.setCode(ErrorCode.NOT_FOUND);
			error.setMessage(se.getMsg() == null ? "required resource is not found" : se.getMsg());
			error.setReason("NotFound");
			status = HttpStatus.NOT_FOUND;
		} 				
	
		return new ResponseEntity<ServiceError> (error, status);
    }  
}
