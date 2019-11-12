package com.shopping.vn.dto;

import java.util.Collection;
import java.util.List;

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
	

}
