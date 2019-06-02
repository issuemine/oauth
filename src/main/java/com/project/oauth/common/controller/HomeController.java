package com.project.oauth.common.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.oauth.user.constant.SessionConstant;
import com.project.oauth.user.domain.User;
import com.project.oauth.user.service.UserService;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<User> user(HttpSession session, HttpServletResponse response) throws IOException {
		Object loginId= session.getAttribute(SessionConstant.SESSION_LOGIN_ID);
		if (loginId == null) {
			response.sendRedirect("/login");
			return null;
		}
		
		String id = (String) loginId;
		Optional<User> user = userService.findById(id);
		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ResponseBody
	@RequestMapping(value = "/users/logout", method = RequestMethod.GET)
	public ResponseEntity<String> logout(HttpSession session, HttpServletResponse response) throws IOException {
		session.removeAttribute(SessionConstant.SESSION_LOGIN_ID);
		session.removeAttribute(SessionConstant.SESSION_ACCESS_TOKEN);
		session.removeAttribute(SessionConstant.SESSION_REFRESH_TOKEN);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
