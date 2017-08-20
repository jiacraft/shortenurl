package com.intuit.shortenurl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.intuit.shortenurl.services.ShortenUrlService;
import com.intuit.shortenurl.services.impl.InMemoryShortenUrlService;

@Configuration
public class ShortenUrlServiceConfig {
	
	@Bean
	public ShortenUrlService shortenUrlService() {
		return new InMemoryShortenUrlService();
	}
}
