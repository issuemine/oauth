package com.project.oauth.user.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.oauth.common.exception.ConnectionException;
import com.project.oauth.common.service.ConnectionService;
import com.project.oauth.common.util.Jackson;
import com.project.oauth.user.constant.SessionConstant;
import com.project.oauth.user.domain.User;
import com.project.oauth.user.service.UserService;

@Controller
public class LoginController {
	@Value("${naver.clientId}")
	private String clientId;

	@Value("${naver.clientSecret}")
	private String clientSecret;

	@Value("${naver.callback.url}")
	private String callbackUrl;

	@Value("${naver.login.api.url}")
	private String loginApiUrl;

	@Value("${naver.auth.api.url}")
	private String authApiUrl;

	@Value("${naver.info.api.url}")
	private String infoApiUrl;

	@Autowired
	private UserService userService;

	@Autowired
	private ConnectionService connectionService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login(Model model, HttpSession session, HttpServletResponse response) throws IOException {
		Object loginId = session.getAttribute("loginId");
		if (loginId != null) {
			response.sendRedirect("/users");
		}
		String redirectUri = URLEncoder.encode(callbackUrl, "UTF-8");
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		StringBuilder loginUrl = new StringBuilder(this.loginApiUrl).append("&").append("client_id=" + clientId)
				.append("&").append("redirect_uri=" + redirectUri).append("&").append("state=" + state);
		response.sendRedirect(loginUrl.toString());
	}

	@RequestMapping(value = "/login/naver/callback", method = RequestMethod.GET)
	public void login(HttpServletRequest request, HttpServletResponse response, String code, String state) throws ConnectionException, IOException {
		String redirectURI = URLEncoder.encode(callbackUrl, "UTF-8");
		StringBuilder queryApiUrl = new StringBuilder(this.authApiUrl).append("&").append("client_id=" + clientId)
				.append("&").append("client_secret=" + clientSecret).append("&").append("redirect_uri=" + redirectURI)
				.append("&").append("code=" + code).append("&").append("state=" + state);

		URL url = new URL(queryApiUrl.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		String responseValue = connectionService.connection(connection);

		Map<Object, Object> responseValueMap = Jackson.stringToMap(responseValue);
		response.sendRedirect("/login/process?accessToken=" + responseValueMap.get("access_token") + "&"
				+ "refreshToken=" + responseValueMap.get("refresh_token") + "&" + "tokenType="
				+ responseValueMap.get("token_type") + "&" + "expiresIn=" + responseValueMap.get("expires_in"));
	}

	@ResponseBody
	@RequestMapping(value = "/login/process", method = RequestMethod.GET)
	public void loginProcess(Model model, HttpSession session, HttpServletResponse response, String accessToken,
			String refreshToken, String tokenType, String expiresIn) throws IOException {
		URL url = new URL(this.infoApiUrl);
		String header = "Bearer  " + accessToken;

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", header);

		String responseValue = connectionService.connection(connection);

		User user = Jackson.stringToUser(responseValue);

		userService.login(user);

		session.setAttribute(SessionConstant.SESSION_LOGIN_ID, user.getUserId());
		session.setAttribute(SessionConstant.SESSION_ACCESS_TOKEN, accessToken);
		session.setAttribute(SessionConstant.SESSION_REFRESH_TOKEN, refreshToken);
		response.sendRedirect("/users");
	}
}
