package com.project.oauth.user.service;

import java.util.Optional;

import com.project.oauth.user.domain.User;

public interface UserService {
	Optional<User> findById(String id);
	User join(User user);
	User login(User user);
}
