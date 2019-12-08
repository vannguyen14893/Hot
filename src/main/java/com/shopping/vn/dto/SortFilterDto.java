package com.shopping.vn.dto;

import java.math.BigDecimal;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Description info field SortFilterDto use filter and sort")
@Data
public class SortFilterDto {
  @ApiModelProperty("Id of role")
  private Long roleId;
  @ApiModelProperty("Status of user")
  private Integer status;
  @ApiModelProperty("Fullname of user")
  private String fullName;
  @ApiModelProperty("Email of user")
  private String email;
  @ApiModelProperty("Start date ")
  private String startDate;
  @ApiModelProperty("End date")
  private String endDate;
  @ApiModelProperty("Use sort filed")
  private Boolean sort;
  @ApiModelProperty("Name sort")
  private String sortName;
  @ApiModelProperty("Page use pagination")
  private Integer page;
  @ApiModelProperty("Page size use pagination")
  private Integer pageSize ;
  @ApiModelProperty("Permission name of user")
  private String permission;
  private List<Long> categoryIds;
  private List<Long> sizeIds;
  private List<Long> colorIds;
  private List<BigDecimal> price;
  private List<BigDecimal> priceSales;
  private List<String> location;
  private List<Integer> votes;
  private List<String> stocks;
  private String title;
  private List<Long> roleIds;
  private List<Long> productIds;
  
}
