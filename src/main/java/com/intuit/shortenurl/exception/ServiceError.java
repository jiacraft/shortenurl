package com.intuit.shortenurl.exception;

import java.io.Serializable;

public class ServiceError implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7140021405116494832L;
	
	//private ErrorCode code;
	private String reason;
	private String message;
	
	/*
	public ErrorCode getCode() {
		return code;
	}
	public void setCode(ErrorCode code) {
		this.code = code;
	} */
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
