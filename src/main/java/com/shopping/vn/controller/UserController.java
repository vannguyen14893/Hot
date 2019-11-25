package com.shopping.vn.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.shopping.vn.utils.ServiceStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
// @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Api(value = "User Management System", description = "Operations pertaining to user in User Management System")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;

//  @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"),
//      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//      @ApiResponse(code = 403,
//          message = "Accessing the resource you were trying to reach is forbidden"),
//      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
	@PostMapping(value = SecurityConstants.LOGIN_URL)
	public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestModel login, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(authentication);

		return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));

	}

	@GetMapping(value = Constants.User.READ_MENU_BY_USER, produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "view menu by user")
	public ResponseEntity<?> readMenuByUser(
			@ApiParam(value = "id of user use get menu ", required = true) @PathVariable("userId") Long userId) {
		List<MenuDto> menus = userService.menus(userId);
		if (CollectionUtils.isEmpty(menus)) {
			return new ResponseEntity<>(ServiceStatus.NO_DATA, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(menus, HttpStatus.OK);
	}

	@PostMapping(value = Constants.User.SIGN_UP_URL, produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "registration user")
	public ResponseEntity<ServiceStatus> resgistrationUser(
			@ApiParam(value = "info user registration ", required = true) @RequestBody UserDto userDto) {
		userService.registrationUser(userDto);
		return new ResponseEntity<>(ServiceStatus.SIGN_UP_URL, HttpStatus.OK);

	}

	@PostMapping(value = Constants.User.CHECK_EMAIL_DONOT_EXIST, produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "check email user do not exit")
	public ResponseEntity<?> checkEmailDonotExit(
			@ApiParam(value = "email of user create ", required = true) @Valid @RequestBody String email,
			BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		if (!userService.checkEmailDoNotExit(email)) {
			return new ResponseEntity<>(ServiceStatus.VALUE_DONOT_EXIST, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(ServiceStatus.VALUE_CAN_USE, HttpStatus.OK);
	}

	@PostMapping(value = Constants.User.ADD_USER, produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "create user")
	public ResponseEntity<?> addUser(
			@ApiParam(value = "info user create ", required = true) @Valid @RequestBody UserDto userDto,
			BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		userService.addUser(userDto);
		return new ResponseEntity<>(ServiceStatus.ADD_SUCCESS, HttpStatus.OK);
	}

	@PostMapping(value = Constants.User.UPDATE_USER, produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "update info user")
	public ResponseEntity<?> updateUser(
			@ApiParam(value = "update User object store in database table", required = true) @Valid @RequestBody UserDto userDto,
			BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		userService.updateUser(userDto);
		return new ResponseEntity<>(ServiceStatus.UPDATE_SUCCESS, HttpStatus.OK);
	}

	@GetMapping(value = Constants.User.DELETE_USER, produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "update status user", response = List.class)
	public ResponseEntity<ServiceStatus> deleteUser(
			@ApiParam(value = "id user use get", required = true) @PathVariable Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(ServiceStatus.DELETE_SUCCESS, HttpStatus.OK);
	}

	@PostMapping(value = "/read-all-user", produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "View a list of available user", response = List.class)
	public ResponseEntity<List<UserDto>> readAll(
			@ApiParam(value = "info user ", required = true) @RequestBody SortFilterDto filter) {
		List<UserDto> readAll = userService.readAll(filter);
		return new ResponseEntity<>(readAll, HttpStatus.OK);

	}

	@ApiOperation(value = "get detail user", response = UserDto.class)
	@PostMapping(value = "/detail-user", produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserDto> getDetail(
			@ApiParam(value = "User object store in database table", required = true) @RequestBody Long id) {
		UserDto user = userService.findById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
