package com.shopping.vn.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.vn.config.JwtTokenProvider;
import com.shopping.vn.dto.MenuDto;
import com.shopping.vn.dto.SortFilterDto;
import com.shopping.vn.dto.UserDto;
import com.shopping.vn.request.JWTLoginSucessReponse;
import com.shopping.vn.request.UserLoginRequestModel;
import com.shopping.vn.service.MapValidationErrorService;
import com.shopping.vn.service.UserService;
import com.shopping.vn.utils.Constants;
import com.shopping.vn.utils.SecurityConstants;

@RestController
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping(value = SecurityConstants.LOGIN_URL)
	public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestModel login, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(authentication);

		return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));

	}

	@GetMapping(value = Constants.User.READ_MENU_BY_USER)
	public ResponseEntity<?> readMenuByUser(@PathVariable("userId") Long userId) {
		List<MenuDto> menus = userService.menus(userId);
		if (CollectionUtils.isEmpty(menus)) {
			return new ResponseEntity<>(Constants.MESSENGER.NO_DATA, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(menus, HttpStatus.OK);
	}

	@PostMapping(value = Constants.User.SIGN_UP_URL)
	public ResponseEntity<?> resgistrationUser(@RequestBody UserDto userDto) {
		userService.registrationUser(userDto);
		return new ResponseEntity<>(Constants.MESSENGER.SIGN_UP_URL, HttpStatus.OK);

	}

	@PostMapping(value = Constants.User.CHECK_EMAIL_DONOT_EXIST)
	public ResponseEntity<?> checkEmailDonotExit(@Valid @RequestBody UserDto userDto, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		if (!userService.checkEmailDoNotExit(userDto.getEmail())) {
			return new ResponseEntity<>(Constants.MESSENGER.VALUE_DONOT_EXIST, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(Constants.MESSENGER.VALUE_CAN_USE, HttpStatus.OK);
	}

	@PostMapping(value = Constants.User.ADD_USER)
	public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		userService.addUser(userDto);
		return new ResponseEntity<>(Constants.MESSENGER.ADD_SUCCESS, HttpStatus.OK);
	}

	@PostMapping(value = Constants.User.UPDATE_USER)
	public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {

		userService.updateUser(userDto);
		return new ResponseEntity<>(Constants.MESSENGER.UPDATE_SUCCESS, HttpStatus.OK);
	}

	@GetMapping(value = Constants.User.DELETE_USER)
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(Constants.MESSENGER.DELETE_SUCCESS, HttpStatus.OK);
	}

	@PostMapping(value = "/read-all-user")
	public ResponseEntity<?> readAll(@RequestBody SortFilterDto filter) {
		
			List<UserDto> readAll = userService.readAll(filter);
			return new ResponseEntity<>(readAll, HttpStatus.OK);
		
		
	}
	@PostMapping(value="/detail-user")
	public ResponseEntity<UserDto> getDetail(@RequestBody Long id){
		UserDto user = userService.findById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
