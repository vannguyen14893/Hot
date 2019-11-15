package com.shopping.vn.dto;

import java.util.List;
import com.shopping.vn.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@ApiModel(description = "Description info field role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
  @ApiModelProperty("Id of role")
  private Long id;
  @ApiModelProperty("name role ")
  private String roleName;
  @ApiModelProperty("list user of role")
  private List<UserDto> users;
  @ApiModelProperty("list permission of role")
  private List<PrivilegeDto> privileges;
  @ApiModelProperty("list menu of role")
  private List<MenuDto> menuDtos;

  public static final RoleDto convert(Role role) {
    RoleDto roleDto = new RoleDto();
    roleDto.setId(role.getId());
    roleDto.setRoleName(role.getRoleName());
    return roleDto;
  }
}
