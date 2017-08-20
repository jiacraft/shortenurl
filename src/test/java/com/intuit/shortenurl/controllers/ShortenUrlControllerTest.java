package com.intuit.shortenurl.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.*;

import com.intuit.shortenurl.exception.ErrorCode;
import com.intuit.shortenurl.exception.UrlShortenServiceException;
import com.intuit.shortenurl.services.ShortenUrlService;

import org.springframework.http.MediaType;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShortenUrlControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private ShortenUrlService svc;

    @Test
    public void getLongUrlNotFound() throws Exception {
    	
    	given(this.svc.retrieveUrl("http://intuit.su/notfound")).willThrow(new UrlShortenServiceException(ErrorCode.NOT_FOUND));
    	
        mvc.perform(MockMvcRequestBuilders.get("/url?shortUrl=http://intuit.su/notfound").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));           
    } 
    
    @Test
    public void getLongUrlInvalidRequest() throws Exception {
    	mvc.perform(MockMvcRequestBuilders.get("/url").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)); 
    }
    
    
    @Test
    public void getLongUrlEmptyUrl() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/url?shortUrl=").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));           
    } 
    
    @Test
    public void getLongUrlSuccess() throws Exception {	
    	given(this.svc.retrieveUrl("http://intuit.su/abcdef")).willReturn("http://returned.original.com");
    
        mvc.perform(MockMvcRequestBuilders.get("/url?shortUrl=http://intuit.su/abcdef").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));           
    }
    
    @Test
    public void getLongUrlInvalidUrl() throws Exception {
    	
    	given(this.svc.retrieveUrl("asdasd")).willThrow(new UrlShortenServiceException(ErrorCode.INVALUD_URL_FORMAT, "the value of shorten URL is not valid"));
    	
        mvc.perform(MockMvcRequestBuilders.get("/url?shortUrl=asdasd").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));           
    } 
    
    @Test
    public void postShortenUrl() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/url").content("{\"longUrl\" : \"http://test.service.com/aaa/bbb\"}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));           
    } 
    
    @Test
    public void postShortenUrlError () throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/url").content("{\"longUrl\" : \"\"}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));           
    } 
    
    @Test
    public void postShortenUrlInvalidUrl () throws Exception {
    	
    	given(this.svc.shortenUrl("aaaaa")).willThrow(new UrlShortenServiceException(ErrorCode.INVALUD_URL_FORMAT));
    	
        mvc.perform(MockMvcRequestBuilders.post("/url").content("{\"longUrl\" : \"aaaaa\"}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));           
    } 
}
