package com.shopping.vn.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import com.shopping.vn.entity.Role;
import com.shopping.vn.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@ApiModel(description = "Description info field user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  @ApiModelProperty("Id of user")
  private Long id;
  @ApiModelProperty("FullName of user")
  private String fullName;
  @NotBlank(message = "Please include a user email")
  @ApiModelProperty("Email of user")
  private String email;
  @ApiModelProperty("Password of user")
  private String password;
  @ApiModelProperty("Mobile of user")
  private String mobile;
  @ApiModelProperty("Status of user")
  private int status;
  @ApiModelProperty("BirthDay of user")
  private Date birthDay;
  @ApiModelProperty("Role of user")
  private List<RoleDto> roles;
  @ApiModelProperty("roleId of user")
  private List<Long> roleIds;
  private ShoppingCartDto shoppingCartDto;
  private String avatar;
  public static final UserDto convertUser(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setFullName(user.getFullName());
    userDto.setEmail(user.getEmail());
    userDto.setMobile(user.getMobile());
    userDto.setStatus(user.getStatus());
    userDto.setAvatar(user.getAvatar());
    userDto.setBirthDay(user.getBirthDay());
    List<Role> roles = user.getRoles();
    List<RoleDto> roleDtos = new ArrayList<>();
    for (Role role : roles) {
      roleDtos.add(RoleDto.convert(role));
      userDto.setRoles(roleDtos);
    }
    return userDto;
  }
}
