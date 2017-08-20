package com.intuit.shortenurl.exception;

public class UrlShortenServiceException extends Exception {
	
	private ErrorCode code;
	private String msg;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4495194370153357912L;
	
	public UrlShortenServiceException(ErrorCode code) {
		this.code = code;
	}
	
	public UrlShortenServiceException(ErrorCode code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ErrorCode getCode() {
		return code;
	}

	public void setCode(ErrorCode code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
