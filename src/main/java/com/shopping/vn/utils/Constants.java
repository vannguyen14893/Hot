package com.shopping.vn.utils;

public class Constants {
	public static class User {
		public static final String READ_MENU_BY_USER = "/read-menu-user/{userId}";
		public static final String SIGN_UP_URL = "/registration-user";
		public static final String CHECK_EMAIL_DONOT_EXIST = "/check-email";
		public static final String ADD_USER = "/add-user";
		public static final String UPDATE_USER = "/update-user";
		public static final String DELETE_USER = "/delete-user/{id}";
	}

	public static class MESSENGER {
		public static final String ADD_SUCCESS = "create success";
		public static final String DELETE_SUCCESS = "delete success";
		public static final String UPDATE_SUCCESS = "update success";
		public static final String SIGN_UP_URL = "registration success";
		public static final String LOGIN_SUCCESS = "login success";
		public static final String VALUE_DONOT_EXIST = "Value do not exist";
		public static final String VALUE_CAN_USE = "Value can use";
		public static final String NO_DATA = "No data";
		public static final String USER_NOT_FOUND="User not found";
		public static final String ROLE_NOT_FOUND="Role not found";
	}
}
