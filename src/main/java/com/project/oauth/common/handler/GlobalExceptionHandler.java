package com.project.oauth.common.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.oauth.common.exception.ConnectionException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler({IOException.class})
	public ResponseEntity<com.project.oauth.common.dto.Error> handleServerError(Exception ex) {
		logger.info(ex.getMessage());
		return new ResponseEntity<>(new com.project.oauth.common.dto.Error(-1, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({ConnectionException.class})
	public ResponseEntity<com.project.oauth.common.dto.Error> handleConnectionError(ConnectionException ex) {
		logger.info(ex.getMessage());
		return new ResponseEntity<>(new com.project.oauth.common.dto.Error(ex.getResponseCode(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
