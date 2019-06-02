package com.project.oauth.common.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.project.oauth.common.exception.ConnectionException;

@Service
public class ConnectionServiceImpl implements ConnectionService {
	@Override
	public String connection(HttpURLConnection connection) throws ConnectionException, IOException {
		int responseCode = connection.getResponseCode();
		BufferedReader br;
		if (responseCode == 200) { // 정상 호출
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} else { // 에러 발생
			throw new ConnectionException(responseCode);
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		br.close();
		return response.toString();
	}

}
