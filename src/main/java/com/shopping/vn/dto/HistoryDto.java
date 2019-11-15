package com.shopping.vn.dto;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Description info field history ")
@Data
public class HistoryDto {
  @ApiModelProperty("Id of history")
  private Long id;
  @ApiModelProperty("description of action save,delete,update")
  private String descrition;
  @ApiModelProperty("date created")
  private Date createdDate;
  @ApiModelProperty("date updated")
  private Date updatedDate;
  @ApiModelProperty("date deleted")
  private Date deletedDate;
  @ApiModelProperty("user created")
  private UserDto createdBy;
  @ApiModelProperty("user updated")
  private UserDto updatedBy;
  @ApiModelProperty("user deleted")
  private UserDto deletedBy;

}
