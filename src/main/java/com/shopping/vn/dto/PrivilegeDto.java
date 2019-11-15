package com.shopping.vn.dto;

import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@ApiModel(description = "Description info field privilege")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeDto {
  @ApiModelProperty("id of privilege")
  private Long id;
  @ApiModelProperty("name of privilege")
  private String name;
  @ApiModelProperty("list role of privilege")
  private List<RoleDto> roles;

}
