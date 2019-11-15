package com.shopping.vn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@ApiModel(description = "Description info field menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
  @ApiModelProperty("id of menu")
  private Long id;
  @ApiModelProperty("name of menu")
  private String name;
  @ApiModelProperty("parent id of menu ")
  private Integer parentId;

}
