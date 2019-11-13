package com.shopping.vn.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.shopping.vn.entity.Role;
import com.shopping.vn.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long id;
	private String fullName;
	@NotBlank(message = "Please include a user email")
	private String email;
	private String password;
	private String mobile;
	private int status;
	private Date birthDay;
	private List<RoleDto> roles;
    private List<Long> roleIds;
	public static final UserDto convertUser(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setFullName(user.getFullName());
		userDto.setEmail(user.getEmail());
		userDto.setMobile(user.getMobile());
		userDto.setStatus(user.getStatus());
		List<Role> roles = user.getRoles();
		List<RoleDto> roleDtos = new ArrayList<>();
		for (Role role : roles) {
			roleDtos.add(RoleDto.convert(role));
			userDto.setRoles(roleDtos);
		}
		return userDto;
	}
}
