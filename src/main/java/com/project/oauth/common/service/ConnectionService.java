package com.project.oauth.common.service;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.project.oauth.common.exception.ConnectionException;

public interface ConnectionService {
	String connection(HttpURLConnection connection) throws ConnectionException, IOException;
}
