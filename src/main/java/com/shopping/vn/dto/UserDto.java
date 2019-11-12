package com.shopping.vn.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto  {
	private Long id;
	private String fullName;
	@NotBlank(message = "Please include a user email")
	private String email;
	private String password;
	private String mobile;
	private int status;
    private List<RoleDto> roles;

}
