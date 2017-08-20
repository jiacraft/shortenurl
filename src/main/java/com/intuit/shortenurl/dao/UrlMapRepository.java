package com.intuit.shortenurl.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.intuit.shortenurl.entities.UrlMap;

/*
@Transactional
public interface UrlMapRepository extends JpaRepository<UrlMap, Long> {
	
	
	final static String QUERY_ORIGINAL_URL = "SELECT m from UrlMap u WHERE u.key = :key ";
	 
    @Query(QUERY_ORIGINAL_URL)
    public UrlMap findByUser(@Param("key") String key);
} */
