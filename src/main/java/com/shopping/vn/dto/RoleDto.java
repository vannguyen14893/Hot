package com.shopping.vn.dto;

import java.util.List;

import com.shopping.vn.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
	private Long id;
	private String roleName;
	private List<UserDto> users;
	private List<PrivilegeDto> privileges;
	private List<MenuDto> menuDtos;

	public static final RoleDto convert(Role role) {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(role.getId());
		roleDto.setRoleName(role.getRoleName());
		return roleDto;
	}
}
