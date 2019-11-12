package com.shopping.vn.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.shopping.vn.dto.MenuDto;
import com.shopping.vn.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto getUser(String email);

	String permissions(Long userId);

	List<MenuDto> menus(Long userId);

	void registrationUser(UserDto userDto);

	boolean checkEmailDoNotExit(String email);

	void addUser(UserDto userDto);

	void updateUser(UserDto userDto);

	void deleteUser(Long id);
}
