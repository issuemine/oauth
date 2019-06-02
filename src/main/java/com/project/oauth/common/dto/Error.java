package com.project.oauth.common.dto;

public class Error {
	private int errorCode;
	private String description;
	
	public Error(int errorCode, String description) {
		this.errorCode = errorCode;
		this.description = description;
	}
}
