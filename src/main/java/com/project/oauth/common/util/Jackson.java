package com.project.oauth.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.oauth.user.domain.User;

public class Jackson {
	public static Map<Object, Object> stringToMap(String stringValue) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<Object, Object> valueMap = new HashMap<>();
		valueMap = objectMapper.readValue(stringValue,  new TypeReference<Map<Object, Object>>() {});
		return valueMap;
	}
	
	public static User stringToUser(String stringValue) throws JsonParseException, JsonMappingException, IOException {
		Map<Object, Object> valueMap = stringToMap(stringValue);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String reponse = objectMapper.writeValueAsString(valueMap.get("response"));
		User user = objectMapper.readValue(reponse, User.class);
		return user;
	}
}
