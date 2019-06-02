package com.project.oauth.user.domain;

import java.beans.Transient;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	@Id
	@JsonProperty("id")
	String userId;
	
	@JsonProperty("email")
	String email;
	
	@JsonProperty("name")
	String name;
	
	@JsonProperty("birthday")
	String birthday;
	
	public User() {}
	
	public User(String userId, String email, String name, String birthday) {
		this.userId = userId;
		this.email = email;
		this.name = name;
		this.birthday = birthday;
	}
	
	public String getUserId() {
		return this.userId;
	}
}
