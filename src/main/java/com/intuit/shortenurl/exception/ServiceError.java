package com.intuit.shortenurl.exception;

public class ServiceError {
	
	private ErrorCode code;
	private String reason;
	private String message;
	
	public ErrorCode getCode() {
		return code;
	}
	public void setCode(ErrorCode code) {
		this.code = code;
	}
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
