package com.project.oauth.common.exception;

public class ConnectionException extends RuntimeException {
	private int responseCode;
	public ConnectionException(int responseCode) {
		super(String.valueOf(responseCode));
		this.responseCode = responseCode;
	}
	
	public int getResponseCode() {
		return this.responseCode;
	}
}
