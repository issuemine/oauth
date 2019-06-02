package com.project.oauth.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.oauth.user.domain.User;
import com.project.oauth.user.respository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User login(User user) {
		Optional<User> existUser = findById(user.getUserId());
		if (!existUser.isPresent()) {
			return join(user);
		}
		return existUser.get();
	}

	@Override
	public Optional<User> findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public User join(User user) {
		return userRepository.save(user);
	}
}
