package com.shopping.vn.utils;

import com.shopping.vn.SpringApplicationContext;
import com.shopping.vn.config.AppProperties;

public class SecurityConstants {
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/add-user";
	public static final String LOGIN_URL = "/login";
	public static final String PERMISSION = "permission";
	public static final String USERSERVICEIMPL = "userServiceImpl";
	public static final long EXPIRATION_TIME = 864000;
	public static final String SECRET = "SecretKeyToGenJWTs";

	public static String getTokenSecret() {
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}

	private SecurityConstants() {

	}
}
